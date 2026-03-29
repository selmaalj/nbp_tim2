package com.jobfair.api.dto.response;

import java.time.LocalDateTime;

public record CommitteeResponse(
        String id,
        Integer year,
        String name,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
