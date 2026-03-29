package com.jobfair.domain.repository;

import java.util.List;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jobfair.domain.model.CommitteeMember;

public interface CommitteeMemberRepository extends GenericRepository<CommitteeMember, String> {

    List<CommitteeMember> findByCommittee_IdOrderByCreatedAtDesc(String committeeId);

    List<CommitteeMember> findByPerson_IdOrderByCreatedAtDesc(String personId);

    @Query("""
	    SELECT cm
	    FROM CommitteeMember cm
	    WHERE (:committeeId IS NULL OR cm.committee.id = :committeeId)
	      AND (:personId IS NULL OR cm.person.id = :personId)
	      AND (:role IS NULL OR LOWER(COALESCE(cm.role, '')) LIKE LOWER(CONCAT('%', :role, '%')))
	    ORDER BY cm.createdAt DESC
	    """)
    List<CommitteeMember> searchRich(
	    @Param("committeeId") String committeeId,
	    @Param("personId") String personId,
	    @Param("role") String role
    );
}
