package com.jobfair.api.controller;

import com.jobfair.api.dto.request.JobRequest;
import com.jobfair.api.dto.response.JobResponse;
import com.jobfair.domain.service.JobService;
import com.jobfair.shared.constants.ApiPaths;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiPaths.JOBS)
@Tag(name = "Jobs")
public class JobController extends AbstractCrudController<String, JobRequest, JobResponse> {

    public JobController(JobService service) {
        super(service);
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
