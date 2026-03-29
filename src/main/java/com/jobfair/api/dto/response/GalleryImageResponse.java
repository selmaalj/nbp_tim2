package com.jobfair.api.dto.response;

import java.time.LocalDateTime;

public record GalleryImageResponse(
        String id,
        String organizationId,
        String mediaId,
        Integer sort,
        String altText,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
