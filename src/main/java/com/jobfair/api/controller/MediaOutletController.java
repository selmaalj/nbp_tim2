package com.jobfair.api.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jobfair.api.dto.request.MediaOutletRequest;
import com.jobfair.api.dto.response.MediaOutletResponse;
import com.jobfair.domain.model.enums.MediaKind;
import com.jobfair.domain.service.MediaOutletService;
import com.jobfair.shared.constants.ApiPaths;
import com.jobfair.shared.docs.ApiResourceDocumentation;
import com.jobfair.shared.docs.DocParameter;
import com.jobfair.shared.docs.EndpointDocumentation;
import com.jobfair.shared.docs.ErrorDocProfile;
import com.jobfair.shared.response.ApiResponse;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;

@RestController
@RequestMapping(ApiPaths.MEDIA_OUTLETS)
@ApiResourceDocumentation(order = 80, singularName = "media outlet", pluralName = "media outlets", sectionTitle = "Media Outlets", snippetPrefix = "media-outlets", sampleId = "outlet-1", description = "Media outlet catalog and kind-based queries.")
@Tag(name = "Media Outlets", description = "Media outlet catalog and kind-based queries.")
@Validated
public class MediaOutletController extends AbstractCrudController<String, MediaOutletRequest, MediaOutletResponse> {

    private final MediaOutletService mediaOutletService;

    public MediaOutletController(MediaOutletService service) {
        super(service);
        this.mediaOutletService = service;
    }

    @GetMapping("/slug/{slug}")
    @EndpointDocumentation(order = 70, snippetId = "media-outlets-slug", displayName = "GET /media-outlets/slug/{slug}", summary = "Get media outlet by slug", pathParameters = @DocParameter(name = "slug", value = "daily-news"), errorProfiles = ErrorDocProfile.NOT_FOUND)
    public ResponseEntity<ApiResponse<MediaOutletResponse>> slug(@PathVariable @NotBlank String slug) {
        MediaOutletResponse payload = mediaOutletService.getBySlug(slug);
        return ResponseEntity.ok(ApiResponse.success("Media outlet by slug", payload));
    }

    @GetMapping("/kind/{kind}")
    @EndpointDocumentation(order = 80, snippetId = "media-outlets-kind", displayName = "GET /media-outlets/kind/{kind}", summary = "Get media outlets by kind", pathParameters = @DocParameter(name = "kind", value = "ONLINE"), errorProfiles = ErrorDocProfile.TYPE_MISMATCH)
    public ResponseEntity<ApiResponse<List<MediaOutletResponse>>> kind(@PathVariable MediaKind kind) {
        List<MediaOutletResponse> payload = mediaOutletService.getByKind(kind);
        return ResponseEntity.ok(ApiResponse.success("Media outlets by kind", payload));
    }

    @GetMapping(params = "q")
    @EndpointDocumentation(order = 90, snippetId = "media-outlets-search", displayName = "GET /media-outlets?q=...", summary = "Search media outlets", queryParameters = @DocParameter(name = "q", value = "daily"))
    public ResponseEntity<ApiResponse<List<MediaOutletResponse>>> search(@RequestParam("q") @NotBlank String q) {
        List<MediaOutletResponse> payload = mediaOutletService.search(q.trim());
        return ResponseEntity.ok(ApiResponse.success("Media outlet search", payload));
    }

    @GetMapping("/filter")
    @EndpointDocumentation(order = 100, snippetId = "media-outlets-filter", displayName = "GET /media-outlets/filter", summary = "Filter media outlets", queryParameters = {
            @DocParameter(name = "q", value = "daily"),
            @DocParameter(name = "kind", value = "ONLINE"),
            @DocParameter(name = "hasWebsite", value = "true")
    }, errorProfiles = ErrorDocProfile.TYPE_MISMATCH)
    public ResponseEntity<ApiResponse<List<MediaOutletResponse>>> filter(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) MediaKind kind,
            @RequestParam(required = false) Boolean hasWebsite
    ) {
        List<MediaOutletResponse> payload = mediaOutletService.filter(trimToNull(q), kind, hasWebsite);
        return ResponseEntity.ok(ApiResponse.success("Media outlet filter", payload));
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
        return "Media outlet";
    }

    @Override
    protected String resourceNamePlural() {
        return "Media outlets";
    }
}
