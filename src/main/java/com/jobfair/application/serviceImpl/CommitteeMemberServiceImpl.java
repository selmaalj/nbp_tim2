package com.jobfair.application.serviceImpl;

import com.jobfair.api.dto.request.CommitteeMemberRequest;
import com.jobfair.api.dto.response.CommitteeMemberResponse;
import com.jobfair.domain.model.CommitteeMember;
import com.jobfair.domain.repository.CommitteeMemberRepository;
import com.jobfair.domain.service.CommitteeMemberService;
import com.jobfair.infrastructure.mapper.BaseMapper;
import org.springframework.stereotype.Service;

@Service
public class CommitteeMemberServiceImpl extends AbstractCrudService<CommitteeMember, String, CommitteeMemberRequest, CommitteeMemberResponse>
        implements CommitteeMemberService {

    public CommitteeMemberServiceImpl(CommitteeMemberRepository repository,
                                      BaseMapper<CommitteeMember, CommitteeMemberRequest, CommitteeMemberResponse> mapper) {
        super(repository, mapper);
    }

    @Override
    protected String resourceName() {
        return "Committee member";
    }
}
