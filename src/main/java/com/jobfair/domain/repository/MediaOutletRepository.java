package com.jobfair.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jobfair.domain.model.MediaOutlet;
import com.jobfair.domain.model.enums.MediaKind;

public interface MediaOutletRepository extends GenericRepository<MediaOutlet, String> {

	Optional<MediaOutlet> findBySlug(String slug);

	@Query("""
			SELECT m
			FROM MediaOutlet m
			WHERE m.kind = :kind
			ORDER BY m.name ASC
			""")
	List<MediaOutlet> findByKindSorted(@Param("kind") MediaKind kind);

	@Query("""
			SELECT m
			FROM MediaOutlet m
			WHERE (:q IS NULL OR LOWER(m.name) LIKE LOWER(CONCAT('%', :q, '%')) OR LOWER(m.slug) LIKE LOWER(CONCAT('%', :q, '%')))
			  AND (:kind IS NULL OR m.kind = :kind)
			  AND (:hasWebsite IS NULL OR (:hasWebsite = TRUE AND m.website IS NOT NULL) OR (:hasWebsite = FALSE AND m.website IS NULL))
			ORDER BY m.name ASC
			""")
	List<MediaOutlet> searchRich(
			@Param("q") String q,
			@Param("kind") MediaKind kind,
			@Param("hasWebsite") Boolean hasWebsite
	);
}
