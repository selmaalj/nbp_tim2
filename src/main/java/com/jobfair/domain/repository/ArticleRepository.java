package com.jobfair.domain.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jobfair.domain.model.Article;

public interface ArticleRepository extends GenericRepository<Article, String> {

	Optional<Article> findBySlug(String slug);

	@Query("""
			SELECT a
			FROM Article a
			WHERE a.published = TRUE
			ORDER BY a.publishedAt DESC
			""")
	List<Article> findPublished();

	@Query("""
			SELECT a
			FROM Article a
			WHERE (:q IS NULL OR LOWER(a.title) LIKE LOWER(CONCAT('%', :q, '%')) OR LOWER(a.body) LIKE LOWER(CONCAT('%', :q, '%')))
			  AND (:published IS NULL OR a.published = :published)
			  AND (:draft IS NULL OR a.draft = :draft)
			  AND (:fromDate IS NULL OR a.createdAt >= :fromDate)
			  AND (:toDate IS NULL OR a.createdAt <= :toDate)
			ORDER BY a.createdAt DESC
			""")
	List<Article> searchRich(
			@Param("q") String q,
			@Param("published") Boolean published,
			@Param("draft") Boolean draft,
			@Param("fromDate") LocalDateTime fromDate,
			@Param("toDate") LocalDateTime toDate
	);
}
