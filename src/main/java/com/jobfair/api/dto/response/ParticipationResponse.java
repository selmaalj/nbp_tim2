package com.jobfair.api.dto.response;

import java.time.LocalDateTime;

public record ParticipationResponse(
        String id,
        String organizationId,
        String packageTierId,
        Integer year,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
