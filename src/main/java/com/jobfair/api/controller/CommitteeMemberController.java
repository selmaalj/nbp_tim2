package com.jobfair.api.controller;

import com.jobfair.api.dto.request.CommitteeMemberRequest;
import com.jobfair.api.dto.response.CommitteeMemberResponse;
import com.jobfair.domain.service.CommitteeMemberService;
import com.jobfair.shared.constants.ApiPaths;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiPaths.COMMITTEE_MEMBERS)
@Tag(name = "Committee members")
public class CommitteeMemberController extends AbstractCrudController<String, CommitteeMemberRequest, CommitteeMemberResponse> {

    public CommitteeMemberController(CommitteeMemberService service) {
        super(service);
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
