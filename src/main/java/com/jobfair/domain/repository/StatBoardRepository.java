package com.jobfair.domain.repository;

import com.jobfair.domain.model.StatBoard;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StatBoardRepository extends GenericRepository<StatBoard, String> {

    Optional<StatBoard> findBySlug(String slug);

    List<StatBoard> findByYearOrderByCreatedAtDesc(Integer year);

    @Query("""
	    SELECT sb
	    FROM StatBoard sb
	    WHERE (:q IS NULL OR LOWER(sb.title) LIKE LOWER(CONCAT('%', :q, '%')) OR LOWER(sb.slug) LIKE LOWER(CONCAT('%', :q, '%')))
	      AND (:year IS NULL OR sb.year = :year)
	    ORDER BY sb.createdAt DESC
	    """)
    List<StatBoard> searchRich(
	    @Param("q") String q,
	    @Param("year") Integer year
    );
}
