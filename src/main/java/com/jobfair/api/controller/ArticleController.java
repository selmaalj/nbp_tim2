package com.jobfair.api.controller;

import com.jobfair.api.dto.request.ArticleRequest;
import com.jobfair.api.dto.response.ArticleResponse;
import com.jobfair.domain.service.ArticleService;
import com.jobfair.shared.constants.ApiPaths;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiPaths.ARTICLES)
@Tag(name = "Articles")
public class ArticleController extends AbstractCrudController<String, ArticleRequest, ArticleResponse> {

    public ArticleController(ArticleService service) {
        super(service);
    }

    @Override
    protected String resourceName() {
        return "Article";
    }

    @Override
    protected String resourceNamePlural() {
        return "Articles";
    }
}
