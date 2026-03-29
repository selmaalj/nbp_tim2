package com.jobfair.api.controller;

import com.jobfair.api.dto.request.CommitteeRequest;
import com.jobfair.api.dto.response.CommitteeResponse;
import com.jobfair.domain.service.CommitteeService;
import com.jobfair.shared.constants.ApiPaths;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiPaths.COMMITTEES)
@Tag(name = "Committees")
public class CommitteeController extends AbstractCrudController<String, CommitteeRequest, CommitteeResponse> {

    public CommitteeController(CommitteeService service) {
        super(service);
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
