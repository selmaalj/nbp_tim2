package com.jobfair.api.dto.response;

import com.jobfair.domain.model.enums.MediaTier;
import java.time.LocalDateTime;

public record MediaParticipationResponse(
        String id,
        String mediaOutletId,
        Integer year,
        MediaTier tier,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
