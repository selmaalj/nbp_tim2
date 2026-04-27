package com.jobfair.api.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jobfair.api.dto.request.OrganizationRequest;
import com.jobfair.api.dto.response.OrganizationResponse;
import com.jobfair.domain.model.enums.OrganizationType;
import com.jobfair.domain.service.OrganizationService;
import com.jobfair.shared.constants.ApiPaths;
import com.jobfair.shared.docs.ApiResourceDocumentation;
import com.jobfair.shared.docs.DocParameter;
import com.jobfair.shared.docs.EndpointDocumentation;
import com.jobfair.shared.docs.ErrorDocProfile;
import com.jobfair.shared.response.ApiResponse;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;

@RestController
@RequestMapping(ApiPaths.ORGANIZATIONS)
@ApiResourceDocumentation(order = 100, singularName = "organization", pluralName = "organizations", sectionTitle = "Organizations", snippetPrefix = "organizations", sampleId = "organization-1", description = "Organization catalog, types, and search.")
@Tag(name = "Organizations", description = "Organization catalog, types, and search.")
@Validated
public class OrganizationController extends AbstractCrudController<String, OrganizationRequest, OrganizationResponse> {

    private final OrganizationService organizationService;

    public OrganizationController(OrganizationService service) {
        super(service);
        this.organizationService = service;
    }

    @GetMapping("/slug/{slug}")
    @EndpointDocumentation(order = 70, snippetId = "organizations-slug", displayName = "GET /organizations/slug/{slug}", summary = "Get organization by slug", pathParameters = @DocParameter(name = "slug", value = "tech-corp"), errorProfiles = ErrorDocProfile.NOT_FOUND)
    public ResponseEntity<ApiResponse<OrganizationResponse>> slug(@PathVariable @NotBlank String slug) {
        OrganizationResponse payload = organizationService.getBySlug(slug);
        return ResponseEntity.ok(ApiResponse.success("Organization by slug", payload));
    }

    @GetMapping("/type/{type}")
    @EndpointDocumentation(order = 80, snippetId = "organizations-type", displayName = "GET /organizations/type/{type}", summary = "Get organizations by type", pathParameters = @DocParameter(name = "type", value = "COMPANY"), errorProfiles = ErrorDocProfile.TYPE_MISMATCH)
    public ResponseEntity<ApiResponse<List<OrganizationResponse>>> type(@PathVariable OrganizationType type) {
        List<OrganizationResponse> payload = organizationService.getByType(type);
        return ResponseEntity.ok(ApiResponse.success("Organizations by type", payload));
    }

    @GetMapping(params = "q")
    @EndpointDocumentation(order = 90, snippetId = "organizations-search", displayName = "GET /organizations?q=...", summary = "Search organizations", queryParameters = @DocParameter(name = "q", value = "tech"))
    public ResponseEntity<ApiResponse<List<OrganizationResponse>>> search(@RequestParam("q") @NotBlank String q) {
        List<OrganizationResponse> payload = organizationService.search(q.trim());
        return ResponseEntity.ok(ApiResponse.success("Organization search", payload));
    }

    @GetMapping("/filter")
    @EndpointDocumentation(order = 100, snippetId = "organizations-filter", displayName = "GET /organizations/filter", summary = "Filter organizations", queryParameters = {
            @DocParameter(name = "q", value = "tech"),
            @DocParameter(name = "type", value = "COMPANY"),
            @DocParameter(name = "hasWebsite", value = "true")
    }, errorProfiles = ErrorDocProfile.TYPE_MISMATCH)
    public ResponseEntity<ApiResponse<List<OrganizationResponse>>> filter(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) OrganizationType type,
            @RequestParam(required = false) Boolean hasWebsite
    ) {
        List<OrganizationResponse> payload = organizationService.filter(trimToNull(q), type, hasWebsite);
        return ResponseEntity.ok(ApiResponse.success("Organization filter", payload));
    }

    private String trimToNull(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
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
