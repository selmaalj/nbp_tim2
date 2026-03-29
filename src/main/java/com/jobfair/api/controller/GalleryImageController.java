package com.jobfair.api.controller;

import com.jobfair.api.dto.request.GalleryImageRequest;
import com.jobfair.api.dto.response.GalleryImageResponse;
import com.jobfair.domain.service.GalleryImageService;
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
@RequestMapping(ApiPaths.GALLERY_IMAGES)
@Tag(name = "Gallery images")
@Validated
public class GalleryImageController extends AbstractCrudController<String, GalleryImageRequest, GalleryImageResponse> {

    private final GalleryImageService galleryImageService;

    public GalleryImageController(GalleryImageService service) {
        super(service);
        this.galleryImageService = service;
    }

    @GetMapping("/organization/{organizationId}")
    public ResponseEntity<ApiResponse<List<GalleryImageResponse>>> organization(@PathVariable @NotBlank String organizationId) {
        List<GalleryImageResponse> payload = galleryImageService.getByOrganizationId(organizationId);
        return ResponseEntity.ok(ApiResponse.success("Gallery images by organization", payload));
    }

    @GetMapping("/media/{mediaId}")
    public ResponseEntity<ApiResponse<List<GalleryImageResponse>>> media(@PathVariable @NotBlank String mediaId) {
        List<GalleryImageResponse> payload = galleryImageService.getByMediaId(mediaId);
        return ResponseEntity.ok(ApiResponse.success("Gallery images by media", payload));
    }

    @GetMapping("/filter")
    public ResponseEntity<ApiResponse<List<GalleryImageResponse>>> filter(
            @RequestParam(required = false) String organizationId,
            @RequestParam(required = false) String mediaId,
            @RequestParam(required = false) String q,
            @RequestParam(required = false) Integer minSort,
            @RequestParam(required = false) Integer maxSort
    ) {
        List<GalleryImageResponse> payload = galleryImageService.filter(trimToNull(organizationId), trimToNull(mediaId), trimToNull(q), minSort, maxSort);
        return ResponseEntity.ok(ApiResponse.success("Gallery image filter", payload));
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
        return "Gallery image";
    }

    @Override
    protected String resourceNamePlural() {
        return "Gallery images";
    }
}
