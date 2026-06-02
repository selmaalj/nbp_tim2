-- Adds indexes for common image lookup endpoints.
-- Idempotent version: if index already exists, ignore ORA-00955.

BEGIN
    EXECUTE IMMEDIATE '
        CREATE INDEX idx_article_images_article_sort
        ON NBPT2.article_images(article_id, display_order)';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -955 THEN
            RAISE;
        END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE '
        CREATE INDEX idx_gallery_images_org_sort
        ON NBPT2.gallery_images(organization_id, display_order)';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -955 THEN
            RAISE;
        END IF;
END;
/