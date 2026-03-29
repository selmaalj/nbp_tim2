package com.jobfair.api.controller;

import com.jobfair.api.dto.request.CommitteeMemberRequest;
import com.jobfair.api.dto.response.CommitteeMemberResponse;
import com.jobfair.domain.service.CommitteeMemberService;
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
@RequestMapping(ApiPaths.COMMITTEE_MEMBERS)
@Tag(name = "Committee members")
@Validated
public class CommitteeMemberController extends AbstractCrudController<String, CommitteeMemberRequest, CommitteeMemberResponse> {

    private final CommitteeMemberService committeeMemberService;

    public CommitteeMemberController(CommitteeMemberService service) {
        super(service);
        this.committeeMemberService = service;
    }

    @GetMapping("/committee/{committeeId}")
    public ResponseEntity<ApiResponse<List<CommitteeMemberResponse>>> committee(@PathVariable @NotBlank String committeeId) {
        List<CommitteeMemberResponse> payload = committeeMemberService.getByCommitteeId(committeeId);
        return ResponseEntity.ok(ApiResponse.success("Committee members by committee", payload));
    }

    @GetMapping("/person/{personId}")
    public ResponseEntity<ApiResponse<List<CommitteeMemberResponse>>> person(@PathVariable @NotBlank String personId) {
        List<CommitteeMemberResponse> payload = committeeMemberService.getByPersonId(personId);
        return ResponseEntity.ok(ApiResponse.success("Committee memberships by person", payload));
    }

    @GetMapping("/filter")
    public ResponseEntity<ApiResponse<List<CommitteeMemberResponse>>> filter(
            @RequestParam(required = false) String committeeId,
            @RequestParam(required = false) String personId,
            @RequestParam(required = false) String role
    ) {
        List<CommitteeMemberResponse> payload = committeeMemberService.filter(trimToNull(committeeId), trimToNull(personId), trimToNull(role));
        return ResponseEntity.ok(ApiResponse.success("Committee member filter", payload));
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
        return "Committee member";
    }

    @Override
    protected String resourceNamePlural() {
        return "Committee members";
    }
}
