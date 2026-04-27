package com.jobfair.api.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jobfair.api.dto.request.PackageTierRequest;
import com.jobfair.api.dto.response.PackageTierResponse;
import com.jobfair.domain.service.PackageTierService;
import com.jobfair.shared.constants.ApiPaths;
import com.jobfair.shared.docs.ApiResourceDocumentation;
import com.jobfair.shared.docs.DocParameter;
import com.jobfair.shared.docs.EndpointDocumentation;
import com.jobfair.shared.docs.ErrorDocProfile;
import com.jobfair.shared.response.ApiResponse;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;

@RestController
@RequestMapping(ApiPaths.PACKAGE_TIERS)
@ApiResourceDocumentation(order = 110, singularName = "package tier", pluralName = "package tiers", sectionTitle = "Package Tiers", snippetPrefix = "package-tiers", sampleId = "package-tier-1", description = "Participation package tiers and code lookup.")
@Tag(name = "Package Tiers", description = "Participation package tiers and code lookup.")
@Validated
public class PackageTierController extends AbstractCrudController<String, PackageTierRequest, PackageTierResponse> {

    private final PackageTierService packageTierService;

    public PackageTierController(PackageTierService service) {
        super(service);
        this.packageTierService = service;
    }

    @GetMapping("/code/{code}")
    @EndpointDocumentation(order = 70, snippetId = "package-tiers-code", displayName = "GET /package-tiers/code/{code}", summary = "Get package tier by code", pathParameters = @DocParameter(name = "code", value = "GOLD"), errorProfiles = ErrorDocProfile.NOT_FOUND)
    public ResponseEntity<ApiResponse<PackageTierResponse>> code(@PathVariable @NotBlank String code) {
        PackageTierResponse payload = packageTierService.getByCode(code);
        return ResponseEntity.ok(ApiResponse.success("Package tier by code", payload));
    }

    @GetMapping(params = "q")
    @EndpointDocumentation(order = 80, snippetId = "package-tiers-search", displayName = "GET /package-tiers?q=...", summary = "Search package tiers", queryParameters = @DocParameter(name = "q", value = "gold"))
    public ResponseEntity<ApiResponse<List<PackageTierResponse>>> search(@RequestParam("q") @NotBlank String q) {
        List<PackageTierResponse> payload = packageTierService.search(q.trim());
        return ResponseEntity.ok(ApiResponse.success("Package tier search", payload));
    }

    @GetMapping("/filter")
    @EndpointDocumentation(order = 90, snippetId = "package-tiers-filter", displayName = "GET /package-tiers/filter", summary = "Filter package tiers", queryParameters = {
            @DocParameter(name = "q", value = "gold"),
            @DocParameter(name = "code", value = "GOLD")
    })
    public ResponseEntity<ApiResponse<List<PackageTierResponse>>> filter(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) String code
    ) {
        List<PackageTierResponse> payload = packageTierService.filter(trimToNull(q), trimToNull(code));
        return ResponseEntity.ok(ApiResponse.success("Package tier filter", payload));
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
        return "Package tier";
    }

    @Override
    protected String resourceNamePlural() {
        return "Package tiers";
    }
}
