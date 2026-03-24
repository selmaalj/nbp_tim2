package com.jobfair.domain.service;

import com.jobfair.api.dto.request.OrganizationRequest;
import com.jobfair.api.dto.response.OrganizationResponse;
import com.jobfair.domain.model.Organization;

public interface OrganizationService extends BaseCrudService<Organization, String, OrganizationRequest, OrganizationResponse> {
}
