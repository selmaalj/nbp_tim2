package com.jobfair.application.serviceImpl;

import com.jobfair.api.dto.request.ArticleRequest;
import com.jobfair.api.dto.response.ArticleResponse;
import com.jobfair.domain.model.Article;
import com.jobfair.domain.repository.ArticleRepository;
import com.jobfair.domain.service.ArticleService;
import com.jobfair.infrastructure.mapper.ArticleMapper;
import org.springframework.stereotype.Service;

@Service
public class ArticleServiceImpl
        extends AbstractCrudService<Article, String, ArticleRequest, ArticleResponse>
        implements ArticleService {

    public ArticleServiceImpl(ArticleRepository repository, ArticleMapper mapper) {
        super(repository, mapper);
    }

    @Override
    protected String resourceName() {
        return "Article";
    }
}
