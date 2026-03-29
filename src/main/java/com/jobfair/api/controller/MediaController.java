package com.jobfair.api.controller;

import com.jobfair.api.dto.request.MediaRequest;
import com.jobfair.api.dto.response.MediaResponse;
import com.jobfair.domain.service.MediaService;
import com.jobfair.shared.constants.ApiPaths;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiPaths.MEDIA)
@Tag(name = "Media")
public class MediaController extends AbstractCrudController<String, MediaRequest, MediaResponse> {

    public MediaController(MediaService service) {
        super(service);
    }

    @Override
    protected String resourceName() {
        return "Media";
    }

    @Override
    protected String resourceNamePlural() {
        return "Media";
    }
}
