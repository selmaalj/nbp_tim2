package com.jobfair.domain.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jobfair.domain.model.Job;

public interface JobRepository extends GenericRepository<Job, String> {

	Optional<Job> findBySlug(String slug);

	@Query("""
			SELECT j
			FROM Job j
			WHERE j.expiresAt IS NULL OR j.expiresAt > :now
			ORDER BY j.postedAt DESC
			""")
	List<Job> findActive(@Param("now") LocalDateTime now);

	@Query("""
			SELECT j
			FROM Job j
			WHERE (:q IS NULL OR LOWER(j.title) LIKE LOWER(CONCAT('%', :q, '%')) OR LOWER(j.description) LIKE LOWER(CONCAT('%', :q, '%')))
			  AND (:postedAfter IS NULL OR j.postedAt >= :postedAfter)
			  AND (:expiresBefore IS NULL OR (j.expiresAt IS NOT NULL AND j.expiresAt <= :expiresBefore))
			  AND (:onlyOpen = FALSE OR j.expiresAt IS NULL OR j.expiresAt > :now)
			ORDER BY j.postedAt DESC
			""")
	List<Job> searchRich(
			@Param("q") String q,
			@Param("postedAfter") LocalDateTime postedAfter,
			@Param("expiresBefore") LocalDateTime expiresBefore,
			@Param("onlyOpen") boolean onlyOpen,
			@Param("now") LocalDateTime now
	);
}
