package com.jobfair.domain.service;

import com.jobfair.api.dto.request.ArticleImageRequest;
import com.jobfair.api.dto.response.ArticleImageResponse;
import com.jobfair.domain.model.ArticleImage;

public interface ArticleImageService extends BaseCrudService<ArticleImage, String, ArticleImageRequest, ArticleImageResponse> {
}
