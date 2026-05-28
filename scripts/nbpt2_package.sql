-- Oracle package: 3 procedures demonstrating INSERT/UPDATE/DELETE use cases
-- Use cases:
--  - Company (organization) create -> INSERT into NBPT2.organizations
--  - Person CV upload -> UPDATE NBPT2.people (cv_data/cv_file_name/cv_mime_type)
--  - Company (organization) delete -> DELETE from NBPT2.organizations

CREATE OR REPLACE PACKAGE NBPT2.jobfair_api_pkg AS

    PROCEDURE create_company(
        p_id IN VARCHAR2,
        p_name IN VARCHAR2,
        p_slug IN VARCHAR2,
        p_website IN VARCHAR2 DEFAULT NULL,
        p_description IN VARCHAR2 DEFAULT NULL,
        p_logo_id IN VARCHAR2 DEFAULT NULL
    );

    PROCEDURE upload_person_cv(
        p_person_id IN VARCHAR2,
        p_cv_data IN BLOB,
        p_file_name IN VARCHAR2,
        p_mime_type IN VARCHAR2
    );

    PROCEDURE delete_company(
        p_id IN VARCHAR2
    );

END jobfair_api_pkg;


CREATE OR REPLACE PACKAGE BODY NBPT2.jobfair_api_pkg AS

    PROCEDURE create_company(
        p_id IN VARCHAR2,
        p_name IN VARCHAR2,
        p_slug IN VARCHAR2,
        p_website IN VARCHAR2 DEFAULT NULL,
        p_description IN VARCHAR2 DEFAULT NULL,
        p_logo_id IN VARCHAR2 DEFAULT NULL
    ) AS
    BEGIN
        IF p_id IS NULL OR TRIM(p_id) IS NULL THEN
            RAISE_APPLICATION_ERROR(-20010, 'create_company: p_id is required');
        END IF;

        IF p_name IS NULL OR TRIM(p_name) IS NULL THEN
            RAISE_APPLICATION_ERROR(-20010, 'create_company: p_name is required');
        END IF;

        IF p_slug IS NULL OR TRIM(p_slug) IS NULL THEN
            RAISE_APPLICATION_ERROR(-20010, 'create_company: p_slug is required');
        END IF;

        INSERT INTO NBPT2.organizations (
            id,
            type,
            name,
            slug,
            website,
            description,
            logo_id,
            created_at,
            updated_at
        ) VALUES (
            p_id,
            'COMPANY',
            p_name,
            p_slug,
            p_website,
            p_description,
            p_logo_id,
            SYSTIMESTAMP,
            NULL
        );
    END create_company;


    PROCEDURE upload_person_cv(
        p_person_id IN VARCHAR2,
        p_cv_data IN BLOB,
        p_file_name IN VARCHAR2,
        p_mime_type IN VARCHAR2
    ) AS
    BEGIN
        IF p_person_id IS NULL OR TRIM(p_person_id) IS NULL THEN
            RAISE_APPLICATION_ERROR(-20011, 'upload_person_cv: p_person_id is required');
        END IF;

        IF p_cv_data IS NULL THEN
            RAISE_APPLICATION_ERROR(-20011, 'upload_person_cv: p_cv_data is required');
        END IF;

        UPDATE NBPT2.people
        SET cv_data = p_cv_data,
            cv_file_name = p_file_name,
            cv_mime_type = p_mime_type,
            updated_at = SYSTIMESTAMP
        WHERE id = p_person_id;

        IF SQL%ROWCOUNT = 0 THEN
            RAISE_APPLICATION_ERROR(-20012, 'upload_person_cv: person not found: ' || p_person_id);
        END IF;
    END upload_person_cv;


    PROCEDURE delete_company(
        p_id IN VARCHAR2
    ) AS
    BEGIN
        IF p_id IS NULL OR TRIM(p_id) IS NULL THEN
            RAISE_APPLICATION_ERROR(-20013, 'delete_company: p_id is required');
        END IF;

        DELETE FROM NBPT2.organizations
        WHERE id = p_id;

        IF SQL%ROWCOUNT = 0 THEN
            RAISE_APPLICATION_ERROR(-20014, 'delete_company: organization not found: ' || p_id);
        END IF;
    END delete_company;

END jobfair_api_pkg;
