package com.jobfair.domain.repository;

import java.util.List;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jobfair.domain.model.GalleryImage;

public interface GalleryImageRepository extends GenericRepository<GalleryImage, String> {

    List<GalleryImage> findByOrganization_IdOrderByDisplayOrderAsc(String organizationId);

    List<GalleryImage> findByMedia_IdOrderByCreatedAtDesc(String mediaId);

    @Query("""
	    SELECT gi
	    FROM GalleryImage gi
	    WHERE (:organizationId IS NULL OR gi.organization.id = :organizationId)
	      AND (:mediaId IS NULL OR gi.media.id = :mediaId)
	      AND (:q IS NULL OR LOWER(COALESCE(gi.caption, '')) LIKE LOWER(CONCAT('%', :q, '%')))
	      AND (:minSort IS NULL OR COALESCE(gi.displayOrder, 0) >= :minSort)
	      AND (:maxSort IS NULL OR COALESCE(gi.displayOrder, 0) <= :maxSort)
	    ORDER BY gi.displayOrder ASC, gi.createdAt DESC
	    """)
    List<GalleryImage> searchRich(
	    @Param("organizationId") String organizationId,
	    @Param("mediaId") String mediaId,
	    @Param("q") String q,
	    @Param("minSort") Integer minSort,
	    @Param("maxSort") Integer maxSort
    );
}
