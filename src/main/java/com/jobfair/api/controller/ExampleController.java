package com.jobfair.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jobfair.api.dto.request.ExampleRequest;
import com.jobfair.api.dto.response.ExampleResponse;
import com.jobfair.domain.service.ExampleService;
import com.jobfair.shared.constants.ApiPaths;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(ApiPaths.EXAMPLES)
@Tag(name = "Example")
public class ExampleController extends AbstractCrudController<Long, ExampleRequest, ExampleResponse> {

    public ExampleController(ExampleService exampleService) {
        super(exampleService);
    }

    @Override
    protected String resourceName() {
        return "Example";
    }

    @Override
    protected String resourceNamePlural() {
        return "Examples";
    }
}
