package com.jobfair.api.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jobfair.api.dto.request.MediaRequest;
import com.jobfair.api.dto.response.MediaResponse;
import com.jobfair.domain.service.MediaService;
import com.jobfair.shared.constants.ApiPaths;
import com.jobfair.shared.docs.ApiResourceDocumentation;
import com.jobfair.shared.docs.DocParameter;
import com.jobfair.shared.docs.EndpointDocumentation;
import com.jobfair.shared.docs.ErrorDocProfile;
import com.jobfair.shared.response.ApiResponse;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;

@RestController
@RequestMapping(ApiPaths.MEDIA)
@ApiResourceDocumentation(order = 70, singularName = "media", pluralName = "media", sectionTitle = "Media", snippetPrefix = "media", sampleId = "media-1", description = "Uploaded media assets and metadata.")
@Tag(name = "Media", description = "Uploaded media assets and metadata.")
@Validated
public class MediaController extends AbstractCrudController<String, MediaRequest, MediaResponse> {

    private final MediaService mediaService;

    public MediaController(MediaService service) {
        super(service);
        this.mediaService = service;
    }

    @GetMapping("/url")
    @EndpointDocumentation(order = 70, snippetId = "media-url", displayName = "GET /media/url", summary = "Get media by URL", queryParameters = @DocParameter(name = "value", value = "https://cdn.jobfair.test/media.png"), errorProfiles = ErrorDocProfile.NOT_FOUND)
    public ResponseEntity<ApiResponse<MediaResponse>> url(@RequestParam @NotBlank String value) {
        MediaResponse payload = mediaService.getByUrl(value.trim());
        return ResponseEntity.ok(ApiResponse.success("Media by url", payload));
    }

    @GetMapping("/mime")
    @EndpointDocumentation(order = 80, snippetId = "media-mime", displayName = "GET /media/mime", summary = "Get media by MIME type", queryParameters = @DocParameter(name = "value", value = "image/png"))
    public ResponseEntity<ApiResponse<List<MediaResponse>>> mime(@RequestParam @NotBlank String value) {
        List<MediaResponse> payload = mediaService.getByMimeType(value.trim());
        return ResponseEntity.ok(ApiResponse.success("Media by mime", payload));
    }

    @GetMapping(params = "q")
    @EndpointDocumentation(order = 90, snippetId = "media-search", displayName = "GET /media?q=...", summary = "Search media", queryParameters = @DocParameter(name = "q", value = "logo"))
    public ResponseEntity<ApiResponse<List<MediaResponse>>> search(@RequestParam("q") @NotBlank String q) {
        List<MediaResponse> payload = mediaService.search(q.trim());
        return ResponseEntity.ok(ApiResponse.success("Media search", payload));
    }

    @GetMapping("/filter")
    @EndpointDocumentation(order = 100, snippetId = "media-filter", displayName = "GET /media/filter", summary = "Filter media", queryParameters = {
            @DocParameter(name = "q", value = "logo"),
            @DocParameter(name = "mimeType", value = "image/png"),
            @DocParameter(name = "minSize", value = "1024"),
            @DocParameter(name = "maxSize", value = "8192")
    }, errorProfiles = ErrorDocProfile.TYPE_MISMATCH)
    public ResponseEntity<ApiResponse<List<MediaResponse>>> filter(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) String mimeType,
            @RequestParam(required = false) Integer minSize,
            @RequestParam(required = false) Integer maxSize
    ) {
        List<MediaResponse> payload = mediaService.filter(trimToNull(q), trimToNull(mimeType), minSize, maxSize);
        return ResponseEntity.ok(ApiResponse.success("Media filter", payload));
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
        return "Media";
    }

    @Override
    protected String resourceNamePlural() {
        return "Media";
    }
}
