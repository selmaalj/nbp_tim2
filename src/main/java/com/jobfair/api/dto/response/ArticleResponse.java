package com.jobfair.api.dto.response;

import java.time.LocalDateTime;

public record ArticleResponse(
        String id,
        String title,
        String slug,
        String body,
        String thumbnailUrl,
        boolean draft,
        boolean published,
        LocalDateTime publishedAt,
        LocalDateTime createdAt
) {
}
