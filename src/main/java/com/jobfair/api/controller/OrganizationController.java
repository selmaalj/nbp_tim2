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
import com.jobfair.shared.response.ApiResponse;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;

@RestController
@RequestMapping(ApiPaths.ORGANIZATIONS)
@Tag(name = "Organizations")
@Validated
public class OrganizationController extends AbstractCrudController<String, OrganizationRequest, OrganizationResponse> {

    private final OrganizationService organizationService;

    public OrganizationController(OrganizationService service) {
        super(service);
        this.organizationService = service;
    }

    @GetMapping("/slug/{slug}")
    public ResponseEntity<ApiResponse<OrganizationResponse>> slug(@PathVariable @NotBlank String slug) {
        OrganizationResponse payload = organizationService.getBySlug(slug);
        return ResponseEntity.ok(ApiResponse.success("Organization by slug", payload));
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<ApiResponse<List<OrganizationResponse>>> type(@PathVariable OrganizationType type) {
        List<OrganizationResponse> payload = organizationService.getByType(type);
        return ResponseEntity.ok(ApiResponse.success("Organizations by type", payload));
    }

    @GetMapping(params = "q")
    public ResponseEntity<ApiResponse<List<OrganizationResponse>>> search(@RequestParam("q") @NotBlank String q) {
        List<OrganizationResponse> payload = organizationService.search(q.trim());
        return ResponseEntity.ok(ApiResponse.success("Organization search", payload));
    }

    @GetMapping("/filter")
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
