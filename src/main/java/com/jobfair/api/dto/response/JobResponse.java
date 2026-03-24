package com.jobfair.api.dto.response;

import java.time.LocalDateTime;

public record JobResponse(
        String id,
        String title,
        String slug,
        String description,
        String applyUrl,
        String applyEmail,
        LocalDateTime postedAt,
        LocalDateTime expiresAt,
        LocalDateTime createdAt
) {
}
