package com.jobfair.application.serviceImpl;

import com.jobfair.api.dto.request.ArticleImageRequest;
import com.jobfair.api.dto.response.ArticleImageResponse;
import com.jobfair.domain.model.ArticleImage;
import com.jobfair.domain.repository.ArticleImageRepository;
import com.jobfair.domain.service.ArticleImageService;
import com.jobfair.infrastructure.mapper.ArticleImageMapper;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ArticleImageServiceImpl extends AbstractCrudService<ArticleImage, String, ArticleImageRequest, ArticleImageResponse>
        implements ArticleImageService {

    private final ArticleImageRepository repository;
    private final ArticleImageMapper mapper;

    public ArticleImageServiceImpl(ArticleImageRepository repository,
                                   ArticleImageMapper mapper) {
        super(repository, mapper);
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ArticleImageResponse> getByArticleId(String articleId) {
        return repository.findByArticle_IdOrderByDisplayOrderAsc(articleId).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ArticleImageResponse> getByMediaId(String mediaId) {
        return repository.findByMedia_IdOrderByCreatedAtDesc(mediaId).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ArticleImageResponse> filter(String articleId, String mediaId, String q, Integer minSort, Integer maxSort) {
        return repository.searchRich(articleId, mediaId, q, minSort, maxSort).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    protected String resourceName() {
        return "Article image";
    }
}
