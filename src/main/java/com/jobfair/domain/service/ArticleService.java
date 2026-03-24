package com.jobfair.domain.service;

import com.jobfair.api.dto.request.ArticleRequest;
import com.jobfair.api.dto.response.ArticleResponse;
import com.jobfair.domain.model.Article;

public interface ArticleService extends BaseCrudService<Article, String, ArticleRequest, ArticleResponse> {
}
