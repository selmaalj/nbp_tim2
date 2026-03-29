package com.jobfair.api.dto.response;

import java.time.LocalDateTime;

public record MediaResponse(
        String id,
        String url,
        Integer width,
        Integer height,
        Integer sizeBytes,
        String mimeType,
        LocalDateTime createdAt
) {
}
