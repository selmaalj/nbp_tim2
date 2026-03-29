package com.jobfair.api.controller;

import com.jobfair.api.dto.request.ParticipationRequest;
import com.jobfair.api.dto.response.ParticipationResponse;
import com.jobfair.domain.service.ParticipationService;
import com.jobfair.shared.constants.ApiPaths;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiPaths.PARTICIPATIONS)
@Tag(name = "Participations")
public class ParticipationController extends AbstractCrudController<String, ParticipationRequest, ParticipationResponse> {

    public ParticipationController(ParticipationService service) {
        super(service);
    }

    @Override
    protected String resourceName() {
        return "Participation";
    }

    @Override
    protected String resourceNamePlural() {
        return "Participations";
    }
}
