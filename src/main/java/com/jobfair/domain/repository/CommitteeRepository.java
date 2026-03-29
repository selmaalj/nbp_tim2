package com.jobfair.domain.repository;

import java.util.List;
import java.util.Optional;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jobfair.domain.model.Committee;

public interface CommitteeRepository extends GenericRepository<Committee, String> {

    Optional<Committee> findByYear(Integer year);

    Optional<Committee> findTopByOrderByYearDesc();

    @Query("""
	    SELECT c
	    FROM Committee c
	    WHERE (:q IS NULL OR LOWER(COALESCE(c.name, '')) LIKE LOWER(CONCAT('%', :q, '%')))
	      AND (:fromYear IS NULL OR c.year >= :fromYear)
	      AND (:toYear IS NULL OR c.year <= :toYear)
	    ORDER BY c.year DESC
	    """)
    List<Committee> searchRich(
	    @Param("q") String q,
	    @Param("fromYear") Integer fromYear,
	    @Param("toYear") Integer toYear
    );
}
