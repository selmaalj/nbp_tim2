package com.jobfair.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jobfair.domain.model.Person;

public interface PersonRepository extends GenericRepository<Person, String> {

	Optional<Person> findByEmailIgnoreCase(String email);

	@Query("""
			SELECT p
			FROM Person p
			WHERE LOWER(COALESCE(p.position, '')) LIKE LOWER(CONCAT('%', :role, '%'))
			ORDER BY p.lastName ASC, p.firstName ASC
			""")
	List<Person> findByRole(@Param("role") String role);

	@Query("""
			SELECT p
			FROM Person p
			WHERE (:q IS NULL OR LOWER(p.firstName) LIKE LOWER(CONCAT('%', :q, '%')) OR LOWER(p.lastName) LIKE LOWER(CONCAT('%', :q, '%')))
			  AND (:role IS NULL OR LOWER(COALESCE(p.position, '')) LIKE LOWER(CONCAT('%', :role, '%')))
			  AND (:hasEmail IS NULL OR (:hasEmail = TRUE AND p.email IS NOT NULL) OR (:hasEmail = FALSE AND p.email IS NULL))
			ORDER BY p.lastName ASC, p.firstName ASC
			""")
	List<Person> searchRich(
			@Param("q") String q,
			@Param("role") String role,
			@Param("hasEmail") Boolean hasEmail
	);
}
