package com.jobfair.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jobfair.domain.model.Organization;
import com.jobfair.domain.model.enums.OrganizationType;

public interface OrganizationRepository extends GenericRepository<Organization, String> {

	Optional<Organization> findBySlug(String slug);

	@Query("""
			SELECT o
			FROM Organization o
			WHERE o.type = :type
			ORDER BY o.name ASC
			""")
	List<Organization> findByTypeSorted(@Param("type") OrganizationType type);

	@Query("""
			SELECT o
			FROM Organization o
			WHERE (:q IS NULL OR LOWER(o.name) LIKE LOWER(CONCAT('%', :q, '%')) OR LOWER(COALESCE(o.description, '')) LIKE LOWER(CONCAT('%', :q, '%')))
			  AND (:type IS NULL OR o.type = :type)
			  AND (:hasWebsite IS NULL OR (:hasWebsite = TRUE AND o.website IS NOT NULL) OR (:hasWebsite = FALSE AND o.website IS NULL))
			ORDER BY o.name ASC
			""")
	List<Organization> searchRich(
			@Param("q") String q,
			@Param("type") OrganizationType type,
			@Param("hasWebsite") Boolean hasWebsite
	);
}
