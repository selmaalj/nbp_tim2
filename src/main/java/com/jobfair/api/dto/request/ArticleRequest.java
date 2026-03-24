package com.jobfair.api.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

public record ArticleRequest(
        @NotNull(message = "Title is required")
        @Size(max = 250, message = "Title must be at most 250 characters")
        String title,

        @NotNull(message = "Slug is required")
        @Size(max = 200, message = "Slug must be at most 200 characters")
        String slug,

        @NotNull(message = "Body is required")
        String body,

        @Size(max = 1024, message = "Thumbnail URL must be at most 1024 characters")
        String thumbnailUrl,

        Boolean draft,

        Boolean published,

        LocalDateTime publishedAt
) {
}
