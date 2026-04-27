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
import com.jobfair.shared.docs.ApiResourceDocumentation;
import com.jobfair.shared.docs.DocParameter;
import com.jobfair.shared.docs.EndpointDocumentation;
import com.jobfair.shared.docs.ErrorDocProfile;
import com.jobfair.shared.response.ApiResponse;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;

@RestController
@RequestMapping(ApiPaths.ARTICLE_IMAGES)
@ApiResourceDocumentation(order = 20, singularName = "article image", pluralName = "article images", sectionTitle = "Article Images", snippetPrefix = "article-images", sampleId = "article-image-1", description = "Article image associations and filter endpoints.")
@Tag(name = "Article Images", description = "Article image associations and filter endpoints.")
@Validated
public class ArticleImageController extends AbstractCrudController<String, ArticleImageRequest, ArticleImageResponse> {

    private final ArticleImageService articleImageService;

    public ArticleImageController(ArticleImageService service) {
        super(service);
        this.articleImageService = service;
    }

    @GetMapping("/article/{articleId}")
    @EndpointDocumentation(order = 70, snippetId = "article-images-by-article", displayName = "GET /article-images/article/{articleId}", summary = "Get article images by article ID", pathParameters = @DocParameter(name = "articleId", value = "article-1"))
    public ResponseEntity<ApiResponse<List<ArticleImageResponse>>> article(@PathVariable @NotBlank String articleId) {
        List<ArticleImageResponse> payload = articleImageService.getByArticleId(articleId);
        return ResponseEntity.ok(ApiResponse.success("Article images by article", payload));
    }

    @GetMapping("/media/{mediaId}")
    @EndpointDocumentation(order = 80, snippetId = "article-images-by-media", displayName = "GET /article-images/media/{mediaId}", summary = "Get article images by media ID", pathParameters = @DocParameter(name = "mediaId", value = "media-1"))
    public ResponseEntity<ApiResponse<List<ArticleImageResponse>>> media(@PathVariable @NotBlank String mediaId) {
        List<ArticleImageResponse> payload = articleImageService.getByMediaId(mediaId);
        return ResponseEntity.ok(ApiResponse.success("Article images by media", payload));
    }

    @GetMapping("/filter")
    @EndpointDocumentation(order = 90, snippetId = "article-images-filter", displayName = "GET /article-images/filter", summary = "Filter article images", queryParameters = {
            @DocParameter(name = "articleId", value = "article-1"),
            @DocParameter(name = "mediaId", value = "media-1"),
            @DocParameter(name = "q", value = "banner"),
            @DocParameter(name = "minSort", value = "1"),
            @DocParameter(name = "maxSort", value = "3")
    }, errorProfiles = ErrorDocProfile.TYPE_MISMATCH)
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
