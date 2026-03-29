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
import com.jobfair.shared.response.ApiResponse;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;

@RestController
@RequestMapping(ApiPaths.ARTICLES)
@Tag(name = "Articles")
@Validated
public class ArticleController extends AbstractCrudController<String, ArticleRequest, ArticleResponse> {

    private final ArticleService articleService;

    public ArticleController(ArticleService service) {
        super(service);
        this.articleService = service;
    }

    @GetMapping("/slug/{slug}")
    public ResponseEntity<ApiResponse<ArticleResponse>> slug(@PathVariable @NotBlank String slug) {
        ArticleResponse payload = articleService.getBySlug(slug);
        return ResponseEntity.ok(ApiResponse.success("Article by slug", payload));
    }

    @GetMapping("/published")
    public ResponseEntity<ApiResponse<List<ArticleResponse>>> published() {
        List<ArticleResponse> payload = articleService.getPublished();
        return ResponseEntity.ok(ApiResponse.success("Published articles", payload));
    }

    @GetMapping(params = "q")
    public ResponseEntity<ApiResponse<List<ArticleResponse>>> search(@RequestParam("q") @NotBlank String q) {
        List<ArticleResponse> payload = articleService.search(q.trim());
        return ResponseEntity.ok(ApiResponse.success("Article search", payload));
    }

    @GetMapping("/filter")
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
