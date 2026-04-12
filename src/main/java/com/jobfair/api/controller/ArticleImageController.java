package com.jobfair.api.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jobfair.api.dto.request.ArticleImageRequest;
import com.jobfair.api.dto.response.ArticleImageResponse;
import com.jobfair.domain.service.ArticleImageService;
import com.jobfair.shared.constants.ApiPaths;
import com.jobfair.shared.response.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;

@RestController
@RequestMapping(ApiPaths.ARTICLE_IMAGES)
@Tag(name = "Article images")
@Validated
public class ArticleImageController extends AbstractCrudController<String, ArticleImageRequest, ArticleImageResponse> {

    private final ArticleImageService articleImageService;

    public ArticleImageController(ArticleImageService service) {
        super(service);
        this.articleImageService = service;
    }

    @GetMapping("/article/{articleId}")
    @Operation(summary = "Get article images by article ID")
    public ResponseEntity<ApiResponse<List<ArticleImageResponse>>> article(@PathVariable @NotBlank String articleId) {
        List<ArticleImageResponse> payload = articleImageService.getByArticleId(articleId);
        return ResponseEntity.ok(ApiResponse.success("Article images by article", payload));
    }

    @GetMapping("/media/{mediaId}")
    @Operation(summary = "Get article images by media ID")
    public ResponseEntity<ApiResponse<List<ArticleImageResponse>>> media(@PathVariable @NotBlank String mediaId) {
        List<ArticleImageResponse> payload = articleImageService.getByMediaId(mediaId);
        return ResponseEntity.ok(ApiResponse.success("Article images by media", payload));
    }

    @GetMapping("/filter")
    @Operation(summary = "Filter article images")
    public ResponseEntity<ApiResponse<List<ArticleImageResponse>>> filter(
            @RequestParam(required = false) String articleId,
            @RequestParam(required = false) String mediaId,
            @RequestParam(required = false) String q,
            @RequestParam(required = false) Integer minSort,
            @RequestParam(required = false) Integer maxSort
    ) {
        List<ArticleImageResponse> payload = articleImageService.filter(trimToNull(articleId), trimToNull(mediaId), trimToNull(q), minSort, maxSort);
        return ResponseEntity.ok(ApiResponse.success("Article image filter", payload));
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
        return "Article image";
    }

    @Override
    protected String resourceNamePlural() {
        return "Article images";
    }
}
