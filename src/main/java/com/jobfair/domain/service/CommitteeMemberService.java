package com.jobfair.domain.service;

import com.jobfair.api.dto.request.CommitteeMemberRequest;
import com.jobfair.api.dto.response.CommitteeMemberResponse;
import com.jobfair.domain.model.CommitteeMember;
import java.util.List;

public interface CommitteeMemberService extends BaseCrudService<CommitteeMember, String, CommitteeMemberRequest, CommitteeMemberResponse> {

	List<CommitteeMemberResponse> getByCommitteeId(String committeeId);

	List<CommitteeMemberResponse> getByPersonId(String personId);

	List<CommitteeMemberResponse> filter(String committeeId, String personId, String role);
}
