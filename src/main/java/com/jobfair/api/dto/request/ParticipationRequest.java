package com.jobfair.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ParticipationRequest(
        @NotBlank(message = "Organization ID is required")
        String organizationId,

        @NotBlank(message = "Package tier ID is required")
        String packageTierId,

        @NotNull(message = "Year is required")
        Integer year
) {
}
