package com.jobfair.domain.service;

import com.jobfair.api.dto.request.ArticleImageRequest;
import com.jobfair.api.dto.response.ArticleImageResponse;
import com.jobfair.domain.model.ArticleImage;
import java.util.List;

public interface ArticleImageService extends BaseCrudService<ArticleImage, String, ArticleImageRequest, ArticleImageResponse> {

	List<ArticleImageResponse> getByArticleId(String articleId);

	List<ArticleImageResponse> getByMediaId(String mediaId);

	List<ArticleImageResponse> filter(String articleId, String mediaId, String q, Integer minSort, Integer maxSort);
}
