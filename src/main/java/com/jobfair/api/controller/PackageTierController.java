package com.jobfair.api.controller;

import com.jobfair.api.dto.request.PackageTierRequest;
import com.jobfair.api.dto.response.PackageTierResponse;
import com.jobfair.domain.service.PackageTierService;
import com.jobfair.shared.constants.ApiPaths;
import com.jobfair.shared.response.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiPaths.PACKAGE_TIERS)
@Tag(name = "Package tiers")
@Validated
public class PackageTierController extends AbstractCrudController<String, PackageTierRequest, PackageTierResponse> {

    private final PackageTierService packageTierService;

    public PackageTierController(PackageTierService service) {
        super(service);
        this.packageTierService = service;
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<ApiResponse<PackageTierResponse>> code(@PathVariable @NotBlank String code) {
        PackageTierResponse payload = packageTierService.getByCode(code);
        return ResponseEntity.ok(ApiResponse.success("Package tier by code", payload));
    }

    @GetMapping(params = "q")
    public ResponseEntity<ApiResponse<List<PackageTierResponse>>> search(@RequestParam("q") @NotBlank String q) {
        List<PackageTierResponse> payload = packageTierService.search(q.trim());
        return ResponseEntity.ok(ApiResponse.success("Package tier search", payload));
    }

    @GetMapping("/filter")
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
