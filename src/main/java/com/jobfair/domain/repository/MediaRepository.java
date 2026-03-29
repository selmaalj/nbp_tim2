package com.jobfair.domain.repository;

import java.util.List;
import java.util.Optional;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jobfair.domain.model.Media;

public interface MediaRepository extends GenericRepository<Media, String> {

    Optional<Media> findByUrl(String url);

    List<Media> findByMimeTypeIgnoreCaseOrderByCreatedAtDesc(String mimeType);

    @Query("""
	    SELECT m
	    FROM Media m
	    WHERE (:q IS NULL OR LOWER(m.url) LIKE LOWER(CONCAT('%', :q, '%')))
	      AND (:mimeType IS NULL OR LOWER(COALESCE(m.mimeType, '')) = LOWER(:mimeType))
	      AND (:minSize IS NULL OR COALESCE(m.sizeBytes, 0) >= :minSize)
	      AND (:maxSize IS NULL OR COALESCE(m.sizeBytes, 0) <= :maxSize)
	    ORDER BY m.createdAt DESC
	    """)
    List<Media> searchRich(
	    @Param("q") String q,
	    @Param("mimeType") String mimeType,
	    @Param("minSize") Integer minSize,
	    @Param("maxSize") Integer maxSize
    );
}
