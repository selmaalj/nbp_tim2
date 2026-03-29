package com.jobfair.application.serviceImpl;

import com.jobfair.api.dto.request.CommitteeMemberRequest;
import com.jobfair.api.dto.response.CommitteeMemberResponse;
import com.jobfair.domain.model.CommitteeMember;
import com.jobfair.domain.repository.CommitteeMemberRepository;
import com.jobfair.domain.service.CommitteeMemberService;
import com.jobfair.infrastructure.mapper.CommitteeMemberMapper;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommitteeMemberServiceImpl extends AbstractCrudService<CommitteeMember, String, CommitteeMemberRequest, CommitteeMemberResponse>
        implements CommitteeMemberService {

    private final CommitteeMemberRepository repository;
    private final CommitteeMemberMapper mapper;

    public CommitteeMemberServiceImpl(CommitteeMemberRepository repository,
                                      CommitteeMemberMapper mapper) {
        super(repository, mapper);
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommitteeMemberResponse> getByCommitteeId(String committeeId) {
        return repository.findByCommittee_IdOrderByCreatedAtDesc(committeeId).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommitteeMemberResponse> getByPersonId(String personId) {
        return repository.findByPerson_IdOrderByCreatedAtDesc(personId).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommitteeMemberResponse> filter(String committeeId, String personId, String role) {
        return repository.searchRich(committeeId, personId, role).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    protected String resourceName() {
        return "Committee member";
    }
}
