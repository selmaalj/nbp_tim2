package com.jobfair.application.serviceImpl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jobfair.api.dto.request.ArticleRequest;
import com.jobfair.api.dto.response.ArticleResponse;
import com.jobfair.domain.model.Article;
import com.jobfair.domain.repository.ArticleRepository;
import com.jobfair.domain.service.ArticleService;
import com.jobfair.infrastructure.mapper.ArticleMapper;
import com.jobfair.shared.exception.ResourceNotFoundException;

@Service
public class ArticleServiceImpl
        extends AbstractCrudService<Article, String, ArticleRequest, ArticleResponse>
        implements ArticleService {

    private final ArticleRepository repository;
    private final ArticleMapper mapper;

    public ArticleServiceImpl(ArticleRepository repository, ArticleMapper mapper) {
        super(repository, mapper);
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    @Transactional(readOnly = true)
    public ArticleResponse getBySlug(String slug) {
        Article article = repository.findBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Article not found with slug: " + slug));
        return mapper.toResponse(article);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ArticleResponse> getPublished() {
        return repository.findPublished().stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ArticleResponse> search(String q) {
        return repository.searchRich(q, null, null, null, null).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ArticleResponse> filter(String q, Boolean published, Boolean draft, LocalDateTime from, LocalDateTime to) {
        return repository.searchRich(q, published, draft, from, to).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    protected String resourceName() {
        return "Article";
    }
}
