package com.jobfair.api.controller;

import com.jobfair.api.dto.request.ArticleImageRequest;
import com.jobfair.api.dto.response.ArticleImageResponse;
import com.jobfair.domain.service.ArticleImageService;
import com.jobfair.shared.constants.ApiPaths;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiPaths.ARTICLE_IMAGES)
@Tag(name = "Article images")
public class ArticleImageController extends AbstractCrudController<String, ArticleImageRequest, ArticleImageResponse> {

    public ArticleImageController(ArticleImageService service) {
        super(service);
    }

    @Override
    protected String resourceName() {
        return "Article image";
    }

    @Override
    protected String resourceNamePlural() {
        return "Article images";
    }
}
