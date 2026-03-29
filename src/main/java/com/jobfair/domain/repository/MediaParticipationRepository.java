package com.jobfair.domain.repository;

import com.jobfair.domain.model.MediaParticipation;
import com.jobfair.domain.model.enums.MediaTier;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MediaParticipationRepository extends GenericRepository<MediaParticipation, String> {

    List<MediaParticipation> findByYearOrderByCreatedAtDesc(Integer year);

    List<MediaParticipation> findByOutlet_IdOrderByYearDesc(String mediaOutletId);

    @Query("""
	    SELECT mp
	    FROM MediaParticipation mp
	    WHERE (:year IS NULL OR mp.year = :year)
	      AND (:tier IS NULL OR mp.tier = :tier)
	      AND (:mediaOutletId IS NULL OR mp.outlet.id = :mediaOutletId)
	    ORDER BY mp.year DESC, mp.createdAt DESC
	    """)
    List<MediaParticipation> searchRich(
	    @Param("year") Integer year,
	    @Param("tier") MediaTier tier,
	    @Param("mediaOutletId") String mediaOutletId
    );
}
