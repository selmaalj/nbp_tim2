package com.jobfair.api.dto.response;

import java.time.LocalDateTime;

public record PackageTierResponse(
        String id,
        String name,
        String tierCode,
        String description,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
