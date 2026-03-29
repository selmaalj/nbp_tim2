package com.jobfair.domain.repository;

import com.jobfair.domain.model.PackageTier;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PackageTierRepository extends GenericRepository<PackageTier, String> {

    Optional<PackageTier> findByTierCodeIgnoreCase(String tierCode);

    @Query("""
	    SELECT pt
	    FROM PackageTier pt
	    WHERE (:q IS NULL OR LOWER(pt.name) LIKE LOWER(CONCAT('%', :q, '%')) OR LOWER(COALESCE(pt.description, '')) LIKE LOWER(CONCAT('%', :q, '%')))
	      AND (:code IS NULL OR LOWER(COALESCE(pt.tierCode, '')) LIKE LOWER(CONCAT('%', :code, '%')))
	    ORDER BY pt.name ASC
	    """)
    List<PackageTier> searchRich(
	    @Param("q") String q,
	    @Param("code") String code
    );
}
