package com.jobfair.api.dto.request;

import com.jobfair.domain.model.enums.MediaTier;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MediaParticipationRequest(
        @NotBlank(message = "Media outlet ID is required")
        String mediaOutletId,

        @NotNull(message = "Year is required")
        Integer year,

        @NotNull(message = "Tier is required")
        MediaTier tier
) {
}
