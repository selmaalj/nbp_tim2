package com.jobfair.domain.repository;

import com.jobfair.domain.model.Participation;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ParticipationRepository extends GenericRepository<Participation, String> {

    List<Participation> findByYearOrderByCreatedAtDesc(Integer year);

    List<Participation> findByOrganization_IdOrderByYearDesc(String organizationId);

    @Query("""
	    SELECT p
	    FROM Participation p
	    WHERE (:year IS NULL OR p.year = :year)
	      AND (:organizationId IS NULL OR p.organization.id = :organizationId)
	      AND (:packageTierId IS NULL OR p.packageTier.id = :packageTierId)
	    ORDER BY p.year DESC, p.createdAt DESC
	    """)
    List<Participation> searchRich(
	    @Param("year") Integer year,
	    @Param("organizationId") String organizationId,
	    @Param("packageTierId") String packageTierId
    );
}
