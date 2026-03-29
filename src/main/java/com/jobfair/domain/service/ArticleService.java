package com.jobfair.domain.service;

import java.time.LocalDateTime;
import java.util.List;

import com.jobfair.api.dto.request.ArticleRequest;
import com.jobfair.api.dto.response.ArticleResponse;
import com.jobfair.domain.model.Article;

public interface ArticleService extends BaseCrudService<Article, String, ArticleRequest, ArticleResponse> {

	ArticleResponse getBySlug(String slug);

	List<ArticleResponse> getPublished();

	List<ArticleResponse> search(String q);

	List<ArticleResponse> filter(String q, Boolean published, Boolean draft, LocalDateTime from, LocalDateTime to);
}
