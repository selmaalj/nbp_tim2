package com.jobfair.api.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jobfair.api.dto.request.CommitteeRequest;
import com.jobfair.api.dto.response.CommitteeResponse;
import com.jobfair.domain.service.CommitteeService;
import com.jobfair.shared.constants.ApiPaths;
import com.jobfair.shared.docs.ApiResourceDocumentation;
import com.jobfair.shared.docs.DocParameter;
import com.jobfair.shared.docs.EndpointDocumentation;
import com.jobfair.shared.docs.ErrorDocProfile;
import com.jobfair.shared.response.ApiResponse;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(ApiPaths.COMMITTEES)
@ApiResourceDocumentation(order = 30, singularName = "committee", pluralName = "committees", sectionTitle = "Committees", snippetPrefix = "committees", sampleId = "committee-1", description = "Committee lifecycle and year-based lookups.")
@Tag(name = "Committees", description = "Committee lifecycle and year-based lookups.")
@Validated
public class CommitteeController extends AbstractCrudController<String, CommitteeRequest, CommitteeResponse> {

    private final CommitteeService committeeService;

    public CommitteeController(CommitteeService service) {
        super(service);
        this.committeeService = service;
    }

    @GetMapping("/year/{year}")
    @EndpointDocumentation(order = 70, snippetId = "committees-year", displayName = "GET /committees/year/{year}", summary = "Get committee by year", pathParameters = @DocParameter(name = "year", value = "2026"), errorProfiles = ErrorDocProfile.NOT_FOUND_AND_TYPE_MISMATCH)
    public ResponseEntity<ApiResponse<CommitteeResponse>> year(@PathVariable Integer year) {
        CommitteeResponse payload = committeeService.getByYear(year);
        return ResponseEntity.ok(ApiResponse.success("Committee by year", payload));
    }

    @GetMapping("/latest")
    @EndpointDocumentation(order = 80, snippetId = "committees-latest", displayName = "GET /committees/latest", summary = "Get latest committee", errorProfiles = ErrorDocProfile.NOT_FOUND)
    public ResponseEntity<ApiResponse<CommitteeResponse>> latest() {
        CommitteeResponse payload = committeeService.getLatest();
        return ResponseEntity.ok(ApiResponse.success("Latest committee", payload));
    }

    @GetMapping(params = "q")
    @EndpointDocumentation(order = 90, snippetId = "committees-search", displayName = "GET /committees?q=...", summary = "Search committees", queryParameters = @DocParameter(name = "q", value = "main"))
    public ResponseEntity<ApiResponse<List<CommitteeResponse>>> search(@RequestParam("q") String q) {
        List<CommitteeResponse> payload = committeeService.search(q.trim());
        return ResponseEntity.ok(ApiResponse.success("Committee search", payload));
    }

    @GetMapping("/filter")
    @EndpointDocumentation(order = 100, snippetId = "committees-filter", displayName = "GET /committees/filter", summary = "Filter committees", queryParameters = {
            @DocParameter(name = "q", value = "main"),
            @DocParameter(name = "fromYear", value = "2024"),
            @DocParameter(name = "toYear", value = "2026")
    }, errorProfiles = ErrorDocProfile.TYPE_MISMATCH)
    public ResponseEntity<ApiResponse<List<CommitteeResponse>>> filter(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) Integer fromYear,
            @RequestParam(required = false) Integer toYear
    ) {
        List<CommitteeResponse> payload = committeeService.filter(trimToNull(q), fromYear, toYear);
        return ResponseEntity.ok(ApiResponse.success("Committee filter", payload));
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
        return "Committee";
    }

    @Override
    protected String resourceNamePlural() {
        return "Committees";
    }
}
