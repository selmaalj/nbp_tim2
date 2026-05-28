-- Adds indexes for common image lookup endpoints.
--
-- Motivating queries (JPA repositories):
--   - ArticleImageRepository.findByArticle_IdOrderByDisplayOrderAsc
--   - GalleryImageRepository.findByOrganization_IdOrderByDisplayOrderAsc
--   - *searchRich(...) endpoints that filter by articleId/organizationId
--
-- These indexes speed up filtering by FK and avoid extra sorting by display_order.

CREATE INDEX idx_article_images_article_sort
    ON NBPT2.article_images(article_id, display_order);

CREATE INDEX idx_gallery_images_org_sort
    ON NBPT2.gallery_images(organization_id, display_order);
