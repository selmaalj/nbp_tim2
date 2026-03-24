package com.jobfair.api.controller;

import com.jobfair.api.dto.request.PersonRequest;
import com.jobfair.api.dto.response.PersonResponse;
import com.jobfair.domain.service.PersonService;
import com.jobfair.shared.constants.ApiPaths;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiPaths.PEOPLE)
@Tag(name = "People")
public class PersonController extends AbstractCrudController<String, PersonRequest, PersonResponse> {

    public PersonController(PersonService service) {
        super(service);
    }

    @Override
    protected String resourceName() {
        return "Person";
    }

    @Override
    protected String resourceNamePlural() {
        return "People";
    }
}
