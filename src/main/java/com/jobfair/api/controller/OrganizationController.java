package com.jobfair.api.controller;

import com.jobfair.api.dto.request.OrganizationRequest;
import com.jobfair.api.dto.response.OrganizationResponse;
import com.jobfair.domain.service.OrganizationService;
import com.jobfair.shared.constants.ApiPaths;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiPaths.ORGANIZATIONS)
@Tag(name = "Organizations")
public class OrganizationController extends AbstractCrudController<String, OrganizationRequest, OrganizationResponse> {

    public OrganizationController(OrganizationService service) {
        super(service);
    }

    @Override
    protected String resourceName() {
        return "Organization";
    }

    @Override
    protected String resourceNamePlural() {
        return "Organizations";
    }
}
