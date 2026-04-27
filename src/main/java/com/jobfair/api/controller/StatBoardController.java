package com.jobfair.api.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jobfair.api.dto.request.StatBoardRequest;
import com.jobfair.api.dto.response.StatBoardResponse;
import com.jobfair.domain.service.StatBoardService;
import com.jobfair.shared.constants.ApiPaths;
import com.jobfair.shared.docs.ApiResourceDocumentation;
import com.jobfair.shared.docs.DocParameter;
import com.jobfair.shared.docs.EndpointDocumentation;
import com.jobfair.shared.docs.ErrorDocProfile;
import com.jobfair.shared.response.ApiResponse;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;

@RestController
@RequestMapping(ApiPaths.STAT_BOARDS)
@ApiResourceDocumentation(order = 140, singularName = "stat board", pluralName = "stat boards", sectionTitle = "Stat Boards", snippetPrefix = "stat-boards", sampleId = "stat-board-1", description = "Stat board catalog, year lookup, and search.")
@Tag(name = "Stat Boards", description = "Stat board catalog, year lookup, and search.")
@Validated
public class StatBoardController extends AbstractCrudController<String, StatBoardRequest, StatBoardResponse> {

    private final StatBoardService statBoardService;

    public StatBoardController(StatBoardService service) {
        super(service);
        this.statBoardService = service;
    }

    @GetMapping("/slug/{slug}")
    @EndpointDocumentation(order = 70, snippetId = "stat-boards-slug", displayName = "GET /stat-boards/slug/{slug}", summary = "Get stat board by slug", pathParameters = @DocParameter(name = "slug", value = "overview-2026"), errorProfiles = ErrorDocProfile.NOT_FOUND)
    public ResponseEntity<ApiResponse<StatBoardResponse>> slug(@PathVariable @NotBlank String slug) {
        StatBoardResponse payload = statBoardService.getBySlug(slug);
        return ResponseEntity.ok(ApiResponse.success("Stat board by slug", payload));
    }

    @GetMapping("/year/{year}")
    @EndpointDocumentation(order = 80, snippetId = "stat-boards-year", displayName = "GET /stat-boards/year/{year}", summary = "Get stat boards by year", pathParameters = @DocParameter(name = "year", value = "2026"), errorProfiles = ErrorDocProfile.TYPE_MISMATCH)
    public ResponseEntity<ApiResponse<List<StatBoardResponse>>> year(@PathVariable Integer year) {
        List<StatBoardResponse> payload = statBoardService.getByYear(year);
        return ResponseEntity.ok(ApiResponse.success("Stat boards by year", payload));
    }

    @GetMapping(params = "q")
    @EndpointDocumentation(order = 90, snippetId = "stat-boards-search", displayName = "GET /stat-boards?q=...", summary = "Search stat boards", queryParameters = @DocParameter(name = "q", value = "overview"))
    public ResponseEntity<ApiResponse<List<StatBoardResponse>>> search(@RequestParam("q") @NotBlank String q) {
        List<StatBoardResponse> payload = statBoardService.search(q.trim());
        return ResponseEntity.ok(ApiResponse.success("Stat board search", payload));
    }

    @GetMapping("/filter")
    @EndpointDocumentation(order = 100, snippetId = "stat-boards-filter", displayName = "GET /stat-boards/filter", summary = "Filter stat boards", queryParameters = {
            @DocParameter(name = "q", value = "overview"),
            @DocParameter(name = "year", value = "2026")
    }, errorProfiles = ErrorDocProfile.TYPE_MISMATCH)
    public ResponseEntity<ApiResponse<List<StatBoardResponse>>> filter(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) Integer year
    ) {
        List<StatBoardResponse> payload = statBoardService.filter(trimToNull(q), year);
        return ResponseEntity.ok(ApiResponse.success("Stat board filter", payload));
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
        return "Stat board";
    }

    @Override
    protected String resourceNamePlural() {
        return "Stat boards";
    }
}
