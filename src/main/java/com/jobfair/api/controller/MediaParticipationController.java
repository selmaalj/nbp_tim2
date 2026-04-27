package com.jobfair.api.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jobfair.api.dto.request.MediaParticipationRequest;
import com.jobfair.api.dto.response.MediaParticipationResponse;
import com.jobfair.domain.model.enums.MediaTier;
import com.jobfair.domain.service.MediaParticipationService;
import com.jobfair.shared.constants.ApiPaths;
import com.jobfair.shared.docs.ApiResourceDocumentation;
import com.jobfair.shared.docs.DocParameter;
import com.jobfair.shared.docs.EndpointDocumentation;
import com.jobfair.shared.docs.ErrorDocProfile;
import com.jobfair.shared.response.ApiResponse;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;

@RestController
@RequestMapping(ApiPaths.MEDIA_PARTICIPATIONS)
@ApiResourceDocumentation(order = 90, singularName = "media participation", pluralName = "media participations", sectionTitle = "Media Participations", snippetPrefix = "media-participations", sampleId = "media-participation-1", description = "Media participation records by year and tier.")
@Tag(name = "Media Participations", description = "Media participation records by year and tier.")
@Validated
public class MediaParticipationController extends AbstractCrudController<String, MediaParticipationRequest, MediaParticipationResponse> {

    private final MediaParticipationService mediaParticipationService;

    public MediaParticipationController(MediaParticipationService service) {
        super(service);
        this.mediaParticipationService = service;
    }

    @GetMapping("/year/{year}")
    @EndpointDocumentation(order = 70, snippetId = "media-participations-year", displayName = "GET /media-participations/year/{year}", summary = "Get media participations by year", pathParameters = @DocParameter(name = "year", value = "2026"), errorProfiles = ErrorDocProfile.TYPE_MISMATCH)
    public ResponseEntity<ApiResponse<List<MediaParticipationResponse>>> year(@PathVariable Integer year) {
        List<MediaParticipationResponse> payload = mediaParticipationService.getByYear(year);
        return ResponseEntity.ok(ApiResponse.success("Media participations by year", payload));
    }

    @GetMapping("/outlet/{mediaOutletId}")
    @EndpointDocumentation(order = 80, snippetId = "media-participations-outlet", displayName = "GET /media-participations/outlet/{mediaOutletId}", summary = "Get media participations by outlet ID", pathParameters = @DocParameter(name = "mediaOutletId", value = "outlet-1"))
    public ResponseEntity<ApiResponse<List<MediaParticipationResponse>>> outlet(@PathVariable @NotBlank String mediaOutletId) {
        List<MediaParticipationResponse> payload = mediaParticipationService.getByMediaOutletId(mediaOutletId);
        return ResponseEntity.ok(ApiResponse.success("Media participations by outlet", payload));
    }

    @GetMapping("/filter")
    @EndpointDocumentation(order = 90, snippetId = "media-participations-filter", displayName = "GET /media-participations/filter", summary = "Filter media participations", queryParameters = {
            @DocParameter(name = "year", value = "2026"),
            @DocParameter(name = "tier", value = "GOLD"),
            @DocParameter(name = "mediaOutletId", value = "outlet-1")
    }, errorProfiles = ErrorDocProfile.TYPE_MISMATCH)
    public ResponseEntity<ApiResponse<List<MediaParticipationResponse>>> filter(
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) MediaTier tier,
            @RequestParam(required = false) String mediaOutletId
    ) {
        List<MediaParticipationResponse> payload = mediaParticipationService.filter(year, tier, trimToNull(mediaOutletId));
        return ResponseEntity.ok(ApiResponse.success("Media participation filter", payload));
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
        return "Media participation";
    }

    @Override
    protected String resourceNamePlural() {
        return "Media participations";
    }
}
