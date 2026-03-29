package com.jobfair.application.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jobfair.api.dto.request.OrganizationRequest;
import com.jobfair.api.dto.response.OrganizationResponse;
import com.jobfair.domain.model.Organization;
import com.jobfair.domain.model.enums.OrganizationType;
import com.jobfair.domain.repository.OrganizationRepository;
import com.jobfair.domain.service.OrganizationService;
import com.jobfair.infrastructure.mapper.OrganizationMapper;
import com.jobfair.shared.exception.ResourceNotFoundException;

@Service
public class OrganizationServiceImpl
        extends AbstractCrudService<Organization, String, OrganizationRequest, OrganizationResponse>
        implements OrganizationService {

    private final OrganizationRepository repository;
    private final OrganizationMapper mapper;

    public OrganizationServiceImpl(OrganizationRepository repository, OrganizationMapper mapper) {
        super(repository, mapper);
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    @Transactional(readOnly = true)
    public OrganizationResponse getBySlug(String slug) {
        Organization organization = repository.findBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Organization not found with slug: " + slug));
        return mapper.toResponse(organization);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrganizationResponse> getByType(OrganizationType type) {
        return repository.findByTypeSorted(type).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrganizationResponse> search(String q) {
        return repository.searchRich(q, null, null).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrganizationResponse> filter(String q, OrganizationType type, Boolean hasWebsite) {
        return repository.searchRich(q, type, hasWebsite).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    protected String resourceName() {
        return "Organization";
    }
}
