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

import com.jobfair.api.dto.request.JobRequest;
import com.jobfair.api.dto.response.JobResponse;
import com.jobfair.domain.service.JobService;
import com.jobfair.shared.constants.ApiPaths;
import com.jobfair.shared.docs.ApiResourceDocumentation;
import com.jobfair.shared.docs.DocParameter;
import com.jobfair.shared.docs.EndpointDocumentation;
import com.jobfair.shared.docs.ErrorDocProfile;
import com.jobfair.shared.response.ApiResponse;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;

@RestController
@RequestMapping(ApiPaths.JOBS)
@ApiResourceDocumentation(order = 60, singularName = "job", pluralName = "jobs", sectionTitle = "Jobs", snippetPrefix = "jobs", sampleId = "job-1", description = "Job listings, search, and activity lookups.")
@Tag(name = "Jobs", description = "Job listings, search, and activity lookups.")
@Validated
public class JobController extends AbstractCrudController<String, JobRequest, JobResponse> {

    private final JobService jobService;

    public JobController(JobService service) {
        super(service);
        this.jobService = service;
    }

    @GetMapping("/slug/{slug}")
    @EndpointDocumentation(order = 70, snippetId = "jobs-slug", displayName = "GET /jobs/slug/{slug}", summary = "Get job by slug", pathParameters = @DocParameter(name = "slug", value = "java-developer"), errorProfiles = ErrorDocProfile.NOT_FOUND)
    public ResponseEntity<ApiResponse<JobResponse>> slug(@PathVariable @NotBlank String slug) {
        JobResponse payload = jobService.getBySlug(slug);
        return ResponseEntity.ok(ApiResponse.success("Job by slug", payload));
    }

    @GetMapping("/active")
    @EndpointDocumentation(order = 80, snippetId = "jobs-active", displayName = "GET /jobs/active", summary = "Get active jobs")
    public ResponseEntity<ApiResponse<List<JobResponse>>> active() {
        List<JobResponse> payload = jobService.getActive();
        return ResponseEntity.ok(ApiResponse.success("Active jobs", payload));
    }

    @GetMapping(params = "q")
    @EndpointDocumentation(order = 90, snippetId = "jobs-search", displayName = "GET /jobs?q=...", summary = "Search jobs", queryParameters = @DocParameter(name = "q", value = "java"))
    public ResponseEntity<ApiResponse<List<JobResponse>>> search(@RequestParam("q") @NotBlank String q) {
        List<JobResponse> payload = jobService.search(q.trim());
        return ResponseEntity.ok(ApiResponse.success("Job search", payload));
    }

    @GetMapping("/filter")
    @EndpointDocumentation(order = 100, snippetId = "jobs-filter", displayName = "GET /jobs/filter", summary = "Filter jobs", queryParameters = {
            @DocParameter(name = "q", value = "developer"),
            @DocParameter(name = "postedAfter", value = "2026-04-01T10:00:00"),
            @DocParameter(name = "expiresBefore", value = "2026-05-01T10:00:00"),
            @DocParameter(name = "openOnly", value = "true")
    }, errorProfiles = ErrorDocProfile.TYPE_MISMATCH)
    public ResponseEntity<ApiResponse<List<JobResponse>>> filter(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime postedAfter,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime expiresBefore,
            @RequestParam(defaultValue = "false") boolean openOnly
    ) {
        List<JobResponse> payload = jobService.filter(trimToNull(q), postedAfter, expiresBefore, openOnly);
        return ResponseEntity.ok(ApiResponse.success("Job filter", payload));
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
        return "Job";
    }

    @Override
    protected String resourceNamePlural() {
        return "Jobs";
    }
}
