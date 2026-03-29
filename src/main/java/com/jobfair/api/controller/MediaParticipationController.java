package com.jobfair.api.controller;

import com.jobfair.api.dto.request.MediaParticipationRequest;
import com.jobfair.api.dto.response.MediaParticipationResponse;
import com.jobfair.domain.service.MediaParticipationService;
import com.jobfair.shared.constants.ApiPaths;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiPaths.MEDIA_PARTICIPATIONS)
@Tag(name = "Media participations")
public class MediaParticipationController extends AbstractCrudController<String, MediaParticipationRequest, MediaParticipationResponse> {

    public MediaParticipationController(MediaParticipationService service) {
        super(service);
    }

    @Override
    protected String resourceName() {
        return "Media participation";
    }

    @Override
    protected String resourceNamePlural() {
        return "Media participations";
    }
}
