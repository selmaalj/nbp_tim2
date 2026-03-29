package com.jobfair.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ArticleImageRequest(
        @NotBlank(message = "Article ID is required")
        String articleId,

        @NotBlank(message = "Media ID is required")
        String mediaId,

        @NotNull(message = "Sort order is required")
        Integer sort,

        String altText
) {
}
