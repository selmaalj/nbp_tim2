package com.jobfair.application.serviceImpl;

import com.jobfair.api.dto.request.ArticleImageRequest;
import com.jobfair.api.dto.response.ArticleImageResponse;
import com.jobfair.domain.model.ArticleImage;
import com.jobfair.domain.repository.ArticleImageRepository;
import com.jobfair.domain.service.ArticleImageService;
import com.jobfair.infrastructure.mapper.BaseMapper;
import org.springframework.stereotype.Service;

@Service
public class ArticleImageServiceImpl extends AbstractCrudService<ArticleImage, String, ArticleImageRequest, ArticleImageResponse>
        implements ArticleImageService {

    public ArticleImageServiceImpl(ArticleImageRepository repository,
                                   BaseMapper<ArticleImage, ArticleImageRequest, ArticleImageResponse> mapper) {
        super(repository, mapper);
    }

    @Override
    protected String resourceName() {
        return "Article image";
    }
}
