package com.jobfair.api.controller;

import com.jobfair.api.dto.request.StatBoardRequest;
import com.jobfair.api.dto.response.StatBoardResponse;
import com.jobfair.domain.service.StatBoardService;
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
@RequestMapping(ApiPaths.STAT_BOARDS)
@Tag(name = "Stat boards")
@Validated
public class StatBoardController extends AbstractCrudController<String, StatBoardRequest, StatBoardResponse> {

    private final StatBoardService statBoardService;

    public StatBoardController(StatBoardService service) {
        super(service);
        this.statBoardService = service;
    }

    @GetMapping("/slug/{slug}")
    public ResponseEntity<ApiResponse<StatBoardResponse>> slug(@PathVariable @NotBlank String slug) {
        StatBoardResponse payload = statBoardService.getBySlug(slug);
        return ResponseEntity.ok(ApiResponse.success("Stat board by slug", payload));
    }

    @GetMapping("/year/{year}")
    public ResponseEntity<ApiResponse<List<StatBoardResponse>>> year(@PathVariable Integer year) {
        List<StatBoardResponse> payload = statBoardService.getByYear(year);
        return ResponseEntity.ok(ApiResponse.success("Stat boards by year", payload));
    }

    @GetMapping(params = "q")
    public ResponseEntity<ApiResponse<List<StatBoardResponse>>> search(@RequestParam("q") @NotBlank String q) {
        List<StatBoardResponse> payload = statBoardService.search(q.trim());
        return ResponseEntity.ok(ApiResponse.success("Stat board search", payload));
    }

    @GetMapping("/filter")
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
