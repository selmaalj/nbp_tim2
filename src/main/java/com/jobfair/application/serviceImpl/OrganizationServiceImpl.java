package com.jobfair.application.serviceImpl;

import com.jobfair.api.dto.request.OrganizationRequest;
import com.jobfair.api.dto.response.OrganizationResponse;
import com.jobfair.domain.model.Organization;
import com.jobfair.domain.repository.OrganizationRepository;
import com.jobfair.domain.service.OrganizationService;
import com.jobfair.infrastructure.mapper.OrganizationMapper;
import org.springframework.stereotype.Service;

@Service
public class OrganizationServiceImpl
        extends AbstractCrudService<Organization, String, OrganizationRequest, OrganizationResponse>
        implements OrganizationService {

    public OrganizationServiceImpl(OrganizationRepository repository, OrganizationMapper mapper) {
        super(repository, mapper);
    }

    @Override
    protected String resourceName() {
        return "Organization";
    }
}
