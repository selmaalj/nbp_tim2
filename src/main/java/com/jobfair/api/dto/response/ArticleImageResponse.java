package com.jobfair.api.dto.response;

import java.time.LocalDateTime;

public record ArticleImageResponse(
        String id,
        String articleId,
        String mediaId,
        Integer sort,
        String altText,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
