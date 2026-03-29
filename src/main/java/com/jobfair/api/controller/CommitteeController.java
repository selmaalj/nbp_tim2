package com.jobfair.api.controller;

import com.jobfair.api.dto.request.CommitteeRequest;
import com.jobfair.api.dto.response.CommitteeResponse;
import com.jobfair.domain.service.CommitteeService;
import com.jobfair.shared.constants.ApiPaths;
import com.jobfair.shared.response.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiPaths.COMMITTEES)
@Tag(name = "Committees")
@Validated
public class CommitteeController extends AbstractCrudController<String, CommitteeRequest, CommitteeResponse> {

    private final CommitteeService committeeService;

    public CommitteeController(CommitteeService service) {
        super(service);
        this.committeeService = service;
    }

    @GetMapping("/year/{year}")
    public ResponseEntity<ApiResponse<CommitteeResponse>> year(@PathVariable Integer year) {
        CommitteeResponse payload = committeeService.getByYear(year);
        return ResponseEntity.ok(ApiResponse.success("Committee by year", payload));
    }

    @GetMapping("/latest")
    public ResponseEntity<ApiResponse<CommitteeResponse>> latest() {
        CommitteeResponse payload = committeeService.getLatest();
        return ResponseEntity.ok(ApiResponse.success("Latest committee", payload));
    }

    @GetMapping(params = "q")
    public ResponseEntity<ApiResponse<List<CommitteeResponse>>> search(@RequestParam("q") String q) {
        List<CommitteeResponse> payload = committeeService.search(q.trim());
        return ResponseEntity.ok(ApiResponse.success("Committee search", payload));
    }

    @GetMapping("/filter")
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
