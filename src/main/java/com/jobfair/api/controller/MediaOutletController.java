package com.jobfair.api.controller;

import com.jobfair.api.dto.request.MediaOutletRequest;
import com.jobfair.api.dto.response.MediaOutletResponse;
import com.jobfair.domain.service.MediaOutletService;
import com.jobfair.shared.constants.ApiPaths;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiPaths.MEDIA_OUTLETS)
@Tag(name = "Media Outlets")
public class MediaOutletController extends AbstractCrudController<String, MediaOutletRequest, MediaOutletResponse> {

    public MediaOutletController(MediaOutletService service) {
        super(service);
    }

    @Override
    protected String resourceName() {
        return "Media outlet";
    }

    @Override
    protected String resourceNamePlural() {
        return "Media outlets";
    }
}
