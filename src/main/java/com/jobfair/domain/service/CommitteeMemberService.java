package com.jobfair.domain.service;

import com.jobfair.api.dto.request.CommitteeMemberRequest;
import com.jobfair.api.dto.response.CommitteeMemberResponse;
import com.jobfair.domain.model.CommitteeMember;

public interface CommitteeMemberService extends BaseCrudService<CommitteeMember, String, CommitteeMemberRequest, CommitteeMemberResponse> {
}
