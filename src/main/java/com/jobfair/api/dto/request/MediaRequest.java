package com.jobfair.api.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record MediaRequest(
        @NotNull(message = "URL is required")
        @Size(max = 1024, message = "URL must be at most 1024 characters")
        String url
) {
}
