package com.jobfair.api.controller;

import com.jobfair.api.dto.request.GalleryImageRequest;
import com.jobfair.api.dto.response.GalleryImageResponse;
import com.jobfair.domain.service.GalleryImageService;
import com.jobfair.shared.constants.ApiPaths;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiPaths.GALLERY_IMAGES)
@Tag(name = "Gallery images")
public class GalleryImageController extends AbstractCrudController<String, GalleryImageRequest, GalleryImageResponse> {

    public GalleryImageController(GalleryImageService service) {
        super(service);
    }

    @Override
    protected String resourceName() {
        return "Gallery image";
    }

    @Override
    protected String resourceNamePlural() {
        return "Gallery images";
    }
}
