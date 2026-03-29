package com.jobfair.application.serviceImpl;

import com.jobfair.api.dto.request.CommitteeRequest;
import com.jobfair.api.dto.response.CommitteeResponse;
import com.jobfair.domain.model.Committee;
import com.jobfair.domain.repository.CommitteeRepository;
import com.jobfair.domain.service.CommitteeService;
import com.jobfair.infrastructure.mapper.BaseMapper;
import org.springframework.stereotype.Service;

@Service
public class CommitteeServiceImpl extends AbstractCrudService<Committee, String, CommitteeRequest, CommitteeResponse>
        implements CommitteeService {

    public CommitteeServiceImpl(CommitteeRepository repository,
                                BaseMapper<Committee, CommitteeRequest, CommitteeResponse> mapper) {
        super(repository, mapper);
    }

    @Override
    protected String resourceName() {
        return "Committee";
    }
}
