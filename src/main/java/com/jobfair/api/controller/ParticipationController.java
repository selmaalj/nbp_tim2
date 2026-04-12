package com.jobfair.api.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jobfair.api.dto.request.ParticipationRequest;
import com.jobfair.api.dto.response.ParticipationResponse;
import com.jobfair.domain.service.ParticipationService;
import com.jobfair.shared.constants.ApiPaths;
import com.jobfair.shared.response.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;

@RestController
@RequestMapping(ApiPaths.PARTICIPATIONS)
@Tag(name = "Participations")
@Validated
public class ParticipationController extends AbstractCrudController<String, ParticipationRequest, ParticipationResponse> {

    private final ParticipationService participationService;

    public ParticipationController(ParticipationService service) {
        super(service);
        this.participationService = service;
    }

    @GetMapping("/year/{year}")
    @Operation(summary = "Get participations by year")
    public ResponseEntity<ApiResponse<List<ParticipationResponse>>> year(@PathVariable Integer year) {
        List<ParticipationResponse> payload = participationService.getByYear(year);
        return ResponseEntity.ok(ApiResponse.success("Participations by year", payload));
    }

    @GetMapping("/organization/{organizationId}")
    @Operation(summary = "Get participations by organization ID")
    public ResponseEntity<ApiResponse<List<ParticipationResponse>>> organization(@PathVariable @NotBlank String organizationId) {
        List<ParticipationResponse> payload = participationService.getByOrganizationId(organizationId);
        return ResponseEntity.ok(ApiResponse.success("Participations by organization", payload));
    }

    @GetMapping("/filter")
    @Operation(summary = "Filter participations")
    public ResponseEntity<ApiResponse<List<ParticipationResponse>>> filter(
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) String organizationId,
            @RequestParam(required = false) String packageTierId
    ) {
        List<ParticipationResponse> payload = participationService.filter(year, trimToNull(organizationId), trimToNull(packageTierId));
        return ResponseEntity.ok(ApiResponse.success("Participation filter", payload));
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
        return "Participation";
    }

    @Override
    protected String resourceNamePlural() {
        return "Participations";
    }
}
