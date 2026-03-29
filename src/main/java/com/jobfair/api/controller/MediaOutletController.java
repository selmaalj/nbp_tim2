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
import com.jobfair.shared.response.ApiResponse;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;

@RestController
@RequestMapping(ApiPaths.MEDIA_OUTLETS)
@Tag(name = "Media Outlets")
@Validated
public class MediaOutletController extends AbstractCrudController<String, MediaOutletRequest, MediaOutletResponse> {

    private final MediaOutletService mediaOutletService;

    public MediaOutletController(MediaOutletService service) {
        super(service);
        this.mediaOutletService = service;
    }

    @GetMapping("/slug/{slug}")
    public ResponseEntity<ApiResponse<MediaOutletResponse>> slug(@PathVariable @NotBlank String slug) {
        MediaOutletResponse payload = mediaOutletService.getBySlug(slug);
        return ResponseEntity.ok(ApiResponse.success("Media outlet by slug", payload));
    }

    @GetMapping("/kind/{kind}")
    public ResponseEntity<ApiResponse<List<MediaOutletResponse>>> kind(@PathVariable MediaKind kind) {
        List<MediaOutletResponse> payload = mediaOutletService.getByKind(kind);
        return ResponseEntity.ok(ApiResponse.success("Media outlets by kind", payload));
    }

    @GetMapping(params = "q")
    public ResponseEntity<ApiResponse<List<MediaOutletResponse>>> search(@RequestParam("q") @NotBlank String q) {
        List<MediaOutletResponse> payload = mediaOutletService.search(q.trim());
        return ResponseEntity.ok(ApiResponse.success("Media outlet search", payload));
    }

    @GetMapping("/filter")
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
