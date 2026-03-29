package com.jobfair.domain.service;

import java.util.List;

import com.jobfair.api.dto.request.OrganizationRequest;
import com.jobfair.api.dto.response.OrganizationResponse;
import com.jobfair.domain.model.Organization;
import com.jobfair.domain.model.enums.OrganizationType;

public interface OrganizationService extends BaseCrudService<Organization, String, OrganizationRequest, OrganizationResponse> {

	OrganizationResponse getBySlug(String slug);

	List<OrganizationResponse> getByType(OrganizationType type);

	List<OrganizationResponse> search(String q);

	List<OrganizationResponse> filter(String q, OrganizationType type, Boolean hasWebsite);
}
