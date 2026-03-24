package com.jobfair.api.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

public record JobRequest(
        @NotNull(message = "Title is required")
        @Size(max = 200, message = "Title must be at most 200 characters")
        String title,

        @NotNull(message = "Slug is required")
        @Size(max = 200, message = "Slug must be at most 200 characters")
        String slug,

        @NotNull(message = "Description is required")
        String description,

        @Size(max = 1024, message = "Apply URL must be at most 1024 characters")
        String applyUrl,

        @Size(max = 160, message = "Apply email must be at most 160 characters")
        String applyEmail,

        LocalDateTime postedAt,

        LocalDateTime expiresAt
) {
}
