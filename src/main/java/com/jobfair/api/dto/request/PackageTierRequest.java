package com.jobfair.api.dto.request;

import jakarta.validation.constraints.NotBlank;

public record PackageTierRequest(
        @NotBlank(message = "Name is required")
        String name,

        String tierCode,

        String description
) {
}
