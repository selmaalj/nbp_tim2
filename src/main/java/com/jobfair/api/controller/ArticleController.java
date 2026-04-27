package com.jobfair.api.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jobfair.api.dto.request.ArticleRequest;
import com.jobfair.api.dto.response.ArticleResponse;
import com.jobfair.domain.service.ArticleService;
import com.jobfair.shared.constants.ApiPaths;
import com.jobfair.shared.docs.ApiResourceDocumentation;
import com.jobfair.shared.docs.DocParameter;
import com.jobfair.shared.docs.EndpointDocumentation;
import com.jobfair.shared.docs.ErrorDocProfile;
import com.jobfair.shared.response.ApiResponse;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;

@RestController
@RequestMapping(ApiPaths.ARTICLES)
@ApiResourceDocumentation(order = 10, singularName = "article", pluralName = "articles", sectionTitle = "Articles", snippetPrefix = "articles", sampleId = "article-1", description = "Article publishing, lookup, and filtering endpoints.")
@Tag(name = "Articles", description = "Article publishing, lookup, and filtering endpoints.")
@Validated
public class ArticleController extends AbstractCrudController<String, ArticleRequest, ArticleResponse> {

    private final ArticleService articleService;

    public ArticleController(ArticleService service) {
        super(service);
        this.articleService = service;
    }

    @GetMapping("/slug/{slug}")
    @EndpointDocumentation(order = 70, snippetId = "articles-slug", displayName = "GET /articles/slug/{slug}", summary = "Get article by slug", pathParameters = @DocParameter(name = "slug", value = "jobfair-2026"), errorProfiles = ErrorDocProfile.NOT_FOUND)
    public ResponseEntity<ApiResponse<ArticleResponse>> slug(@PathVariable @NotBlank String slug) {
        ArticleResponse payload = articleService.getBySlug(slug);
        return ResponseEntity.ok(ApiResponse.success("Article by slug", payload));
    }

    @GetMapping("/published")
    @EndpointDocumentation(order = 80, snippetId = "articles-published", displayName = "GET /articles/published", summary = "Get published articles")
    public ResponseEntity<ApiResponse<List<ArticleResponse>>> published() {
        List<ArticleResponse> payload = articleService.getPublished();
        return ResponseEntity.ok(ApiResponse.success("Published articles", payload));
    }

    @GetMapping(params = "q")
    @EndpointDocumentation(order = 90, snippetId = "articles-search", displayName = "GET /articles?q=...", summary = "Search articles", queryParameters = @DocParameter(name = "q", value = "jobfair"))
    public ResponseEntity<ApiResponse<List<ArticleResponse>>> search(@RequestParam("q") @NotBlank String q) {
        List<ArticleResponse> payload = articleService.search(q.trim());
        return ResponseEntity.ok(ApiResponse.success("Article search", payload));
    }

    @GetMapping("/filter")
    @EndpointDocumentation(order = 100, snippetId = "articles-filter", displayName = "GET /articles/filter", summary = "Filter articles", queryParameters = {
            @DocParameter(name = "q", value = "jobfair"),
            @DocParameter(name = "published", value = "true"),
            @DocParameter(name = "draft", value = "false"),
            @DocParameter(name = "from", value = "2026-04-01T10:00:00"),
            @DocParameter(name = "to", value = "2026-04-30T18:00:00")
    }, errorProfiles = ErrorDocProfile.TYPE_MISMATCH)
    public ResponseEntity<ApiResponse<List<ArticleResponse>>> filter(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) Boolean published,
            @RequestParam(required = false) Boolean draft,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to
    ) {
        List<ArticleResponse> payload = articleService.filter(trimToNull(q), published, draft, from, to);
        return ResponseEntity.ok(ApiResponse.success("Article filter", payload));
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
        return "Article";
    }

    @Override
    protected String resourceNamePlural() {
        return "Articles";
    }
}
