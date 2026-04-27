package com.jobfair.api.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jobfair.api.dto.request.CommitteeMemberRequest;
import com.jobfair.api.dto.response.CommitteeMemberResponse;
import com.jobfair.domain.service.CommitteeMemberService;
import com.jobfair.shared.constants.ApiPaths;
import com.jobfair.shared.docs.ApiResourceDocumentation;
import com.jobfair.shared.docs.DocParameter;
import com.jobfair.shared.docs.EndpointDocumentation;
import com.jobfair.shared.response.ApiResponse;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;

@RestController
@RequestMapping(ApiPaths.COMMITTEE_MEMBERS)
@ApiResourceDocumentation(order = 40, singularName = "committee member", pluralName = "committee members", sectionTitle = "Committee Members", snippetPrefix = "committee-members", sampleId = "committee-member-1", description = "Committee membership relations and filters.")
@Tag(name = "Committee Members", description = "Committee membership relations and filters.")
@Validated
public class CommitteeMemberController extends AbstractCrudController<String, CommitteeMemberRequest, CommitteeMemberResponse> {

    private final CommitteeMemberService committeeMemberService;

    public CommitteeMemberController(CommitteeMemberService service) {
        super(service);
        this.committeeMemberService = service;
    }

    @GetMapping("/committee/{committeeId}")
    @EndpointDocumentation(order = 70, snippetId = "committee-members-by-committee", displayName = "GET /committee-members/committee/{committeeId}", summary = "Get committee members by committee ID", pathParameters = @DocParameter(name = "committeeId", value = "committee-1"))
    public ResponseEntity<ApiResponse<List<CommitteeMemberResponse>>> committee(@PathVariable @NotBlank String committeeId) {
        List<CommitteeMemberResponse> payload = committeeMemberService.getByCommitteeId(committeeId);
        return ResponseEntity.ok(ApiResponse.success("Committee members by committee", payload));
    }

    @GetMapping("/person/{personId}")
    @EndpointDocumentation(order = 80, snippetId = "committee-members-by-person", displayName = "GET /committee-members/person/{personId}", summary = "Get committee memberships by person ID", pathParameters = @DocParameter(name = "personId", value = "person-1"))
    public ResponseEntity<ApiResponse<List<CommitteeMemberResponse>>> person(@PathVariable @NotBlank String personId) {
        List<CommitteeMemberResponse> payload = committeeMemberService.getByPersonId(personId);
        return ResponseEntity.ok(ApiResponse.success("Committee memberships by person", payload));
    }

    @GetMapping("/filter")
    @EndpointDocumentation(order = 90, snippetId = "committee-members-filter", displayName = "GET /committee-members/filter", summary = "Filter committee memberships", queryParameters = {
            @DocParameter(name = "committeeId", value = "committee-1"),
            @DocParameter(name = "personId", value = "person-1"),
            @DocParameter(name = "role", value = "Lead")
    })
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
