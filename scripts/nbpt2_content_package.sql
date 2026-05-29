-- Oracle package: 3 procedures demonstrating multi-table INSERT/UPDATE and computed fields
-- Use cases (present in the app):
--  - Job create with tags -> INSERT into NBPT2.jobs + NBPT2.job_tags
--  - Article publish -> UPDATE NBPT2.articles (published/draft/published_at)
--  - Organization gallery image add -> INSERT into NBPT2.gallery_images with auto display_order

CREATE OR REPLACE PACKAGE NBPT2.jobfair_content_pkg AS

    PROCEDURE create_job_with_tags(
        p_job_id IN VARCHAR2,
        p_title IN VARCHAR2,
        p_slug IN VARCHAR2,
        p_description IN CLOB,
        p_apply_url IN VARCHAR2 DEFAULT NULL,
        p_apply_email IN VARCHAR2 DEFAULT NULL,
        p_organization_id IN VARCHAR2,
        p_expires_at IN TIMESTAMP DEFAULT NULL,
        p_posted_at IN TIMESTAMP DEFAULT NULL,
        p_tags_csv IN VARCHAR2 DEFAULT NULL
    );

    PROCEDURE publish_article(
        p_article_id IN VARCHAR2
    );

    PROCEDURE add_gallery_image_auto_order(
        p_id IN VARCHAR2,
        p_organization_id IN VARCHAR2,
        p_media_id IN VARCHAR2,
        p_caption IN VARCHAR2 DEFAULT NULL
    );

END jobfair_content_pkg;
/


CREATE OR REPLACE PACKAGE BODY NBPT2.jobfair_content_pkg AS

    PROCEDURE create_job_with_tags(
        p_job_id IN VARCHAR2,
        p_title IN VARCHAR2,
        p_slug IN VARCHAR2,
        p_description IN CLOB,
        p_apply_url IN VARCHAR2 DEFAULT NULL,
        p_apply_email IN VARCHAR2 DEFAULT NULL,
        p_organization_id IN VARCHAR2,
        p_expires_at IN TIMESTAMP DEFAULT NULL,
        p_posted_at IN TIMESTAMP DEFAULT NULL,
        p_tags_csv IN VARCHAR2 DEFAULT NULL
    ) AS
        v_posted_at TIMESTAMP;
        v_tag VARCHAR2(100);
        v_pos PLS_INTEGER := 1;
    BEGIN
        IF p_job_id IS NULL OR TRIM(p_job_id) IS NULL THEN
            RAISE_APPLICATION_ERROR(-20020, 'create_job_with_tags: p_job_id is required');
        END IF;

        IF p_title IS NULL OR TRIM(p_title) IS NULL THEN
            RAISE_APPLICATION_ERROR(-20020, 'create_job_with_tags: p_title is required');
        END IF;

        IF p_slug IS NULL OR TRIM(p_slug) IS NULL THEN
            RAISE_APPLICATION_ERROR(-20020, 'create_job_with_tags: p_slug is required');
        END IF;

        IF p_description IS NULL THEN
            RAISE_APPLICATION_ERROR(-20020, 'create_job_with_tags: p_description is required');
        END IF;

        IF p_organization_id IS NULL OR TRIM(p_organization_id) IS NULL THEN
            RAISE_APPLICATION_ERROR(-20020, 'create_job_with_tags: p_organization_id is required');
        END IF;

        v_posted_at := COALESCE(p_posted_at, CAST(SYSTIMESTAMP AS TIMESTAMP));

        INSERT INTO NBPT2.jobs (
            id,
            title,
            slug,
            description,
            apply_url,
            apply_email,
            posted_at,
            expires_at,
            organization_id,
            created_at,
            updated_at
        ) VALUES (
            p_job_id,
            p_title,
            p_slug,
            p_description,
            p_apply_url,
            p_apply_email,
            v_posted_at,
            p_expires_at,
            p_organization_id,
            SYSTIMESTAMP,
            NULL
        );

        -- Tags: CSV like 'java,backend,internship'. Spaces are trimmed.
        -- If p_tags_csv is NULL/blank, no tags are inserted.
        IF p_tags_csv IS NOT NULL AND TRIM(p_tags_csv) IS NOT NULL THEN
            LOOP
                v_tag := REGEXP_SUBSTR(p_tags_csv, '[^,]+', 1, v_pos);
                EXIT WHEN v_tag IS NULL;

                v_tag := LOWER(TRIM(v_tag));
                IF v_tag IS NOT NULL AND v_tag <> '' THEN
                    BEGIN
                        INSERT INTO NBPT2.job_tags (job_id, tag_value)
                        VALUES (p_job_id, v_tag);
                    EXCEPTION
                        WHEN DUP_VAL_ON_INDEX THEN
                            NULL;
                    END;
                END IF;

                v_pos := v_pos + 1;
            END LOOP;
        END IF;
    END create_job_with_tags;


    PROCEDURE publish_article(
        p_article_id IN VARCHAR2
    ) AS
        v_exists NUMBER(1);
    BEGIN
        IF p_article_id IS NULL OR TRIM(p_article_id) IS NULL THEN
            RAISE_APPLICATION_ERROR(-20021, 'publish_article: p_article_id is required');
        END IF;

        SELECT 1
        INTO v_exists
        FROM NBPT2.articles
        WHERE id = p_article_id;

        UPDATE NBPT2.articles
        SET published = 1,
            draft = 0,
            published_at = SYSTIMESTAMP,
            updated_at = SYSTIMESTAMP
        WHERE id = p_article_id;
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            RAISE_APPLICATION_ERROR(-20022, 'publish_article: article not found: ' || p_article_id);
    END publish_article;


    PROCEDURE add_gallery_image_auto_order(
        p_id IN VARCHAR2,
        p_organization_id IN VARCHAR2,
        p_media_id IN VARCHAR2,
        p_caption IN VARCHAR2 DEFAULT NULL
    ) AS
        v_next_order NUMBER(10);
    BEGIN
        IF p_id IS NULL OR TRIM(p_id) IS NULL THEN
            RAISE_APPLICATION_ERROR(-20023, 'add_gallery_image_auto_order: p_id is required');
        END IF;

        IF p_organization_id IS NULL OR TRIM(p_organization_id) IS NULL THEN
            RAISE_APPLICATION_ERROR(-20023, 'add_gallery_image_auto_order: p_organization_id is required');
        END IF;

        IF p_media_id IS NULL OR TRIM(p_media_id) IS NULL THEN
            RAISE_APPLICATION_ERROR(-20023, 'add_gallery_image_auto_order: p_media_id is required');
        END IF;

        SELECT COALESCE(MAX(display_order), 0) + 1
        INTO v_next_order
        FROM NBPT2.gallery_images
        WHERE organization_id = p_organization_id;

        INSERT INTO NBPT2.gallery_images (
            id,
            organization_id,
            media_id,
            caption,
            display_order,
            created_at,
            updated_at
        ) VALUES (
            p_id,
            p_organization_id,
            p_media_id,
            p_caption,
            v_next_order,
            SYSTIMESTAMP,
            NULL
        );
    END add_gallery_image_auto_order;

END jobfair_content_pkg;
/
