package com.jobfair.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CommitteeRequest(
        @NotNull(message = "Year is required")
        Integer year,

        @NotBlank(message = "Name is required")
        String name
) {
}
