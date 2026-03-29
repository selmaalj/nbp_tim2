package com.jobfair.domain.repository;

import java.util.List;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jobfair.domain.model.ArticleImage;

public interface ArticleImageRepository extends GenericRepository<ArticleImage, String> {

    List<ArticleImage> findByArticle_IdOrderByDisplayOrderAsc(String articleId);

    List<ArticleImage> findByMedia_IdOrderByCreatedAtDesc(String mediaId);

    @Query("""
	    SELECT ai
	    FROM ArticleImage ai
	    WHERE (:articleId IS NULL OR ai.article.id = :articleId)
	      AND (:mediaId IS NULL OR ai.media.id = :mediaId)
	      AND (:q IS NULL OR LOWER(COALESCE(ai.caption, '')) LIKE LOWER(CONCAT('%', :q, '%')))
	      AND (:minSort IS NULL OR COALESCE(ai.displayOrder, 0) >= :minSort)
	      AND (:maxSort IS NULL OR COALESCE(ai.displayOrder, 0) <= :maxSort)
	    ORDER BY ai.displayOrder ASC, ai.createdAt DESC
	    """)
    List<ArticleImage> searchRich(
	    @Param("articleId") String articleId,
	    @Param("mediaId") String mediaId,
	    @Param("q") String q,
	    @Param("minSort") Integer minSort,
	    @Param("maxSort") Integer maxSort
    );
}
