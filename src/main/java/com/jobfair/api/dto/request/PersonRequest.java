package com.jobfair.api.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PersonRequest(
        @NotNull(message = "First name is required")
        @Size(max = 120, message = "First name must be at most 120 characters")
        String firstName,

        @NotNull(message = "Last name is required")
        @Size(max = 120, message = "Last name must be at most 120 characters")
        String lastName,

        @Size(max = 160, message = "Email must be at most 160 characters")
        String email,

        @Size(max = 50, message = "Phone must be at most 50 characters")
        String phone,

        @Size(max = 120, message = "Position must be at most 120 characters")
        String position
) {
}
