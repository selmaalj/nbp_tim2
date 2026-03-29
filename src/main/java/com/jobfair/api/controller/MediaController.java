package com.jobfair.api.controller;

import com.jobfair.api.dto.request.MediaRequest;
import com.jobfair.api.dto.response.MediaResponse;
import com.jobfair.domain.service.MediaService;
import com.jobfair.shared.constants.ApiPaths;
import com.jobfair.shared.response.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiPaths.MEDIA)
@Tag(name = "Media")
@Validated
public class MediaController extends AbstractCrudController<String, MediaRequest, MediaResponse> {

    private final MediaService mediaService;

    public MediaController(MediaService service) {
        super(service);
        this.mediaService = service;
    }

    @GetMapping("/url")
    public ResponseEntity<ApiResponse<MediaResponse>> url(@RequestParam @NotBlank String value) {
        MediaResponse payload = mediaService.getByUrl(value.trim());
        return ResponseEntity.ok(ApiResponse.success("Media by url", payload));
    }

    @GetMapping("/mime")
    public ResponseEntity<ApiResponse<List<MediaResponse>>> mime(@RequestParam @NotBlank String value) {
        List<MediaResponse> payload = mediaService.getByMimeType(value.trim());
        return ResponseEntity.ok(ApiResponse.success("Media by mime", payload));
    }

    @GetMapping(params = "q")
    public ResponseEntity<ApiResponse<List<MediaResponse>>> search(@RequestParam("q") @NotBlank String q) {
        List<MediaResponse> payload = mediaService.search(q.trim());
        return ResponseEntity.ok(ApiResponse.success("Media search", payload));
    }

    @GetMapping("/filter")
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
