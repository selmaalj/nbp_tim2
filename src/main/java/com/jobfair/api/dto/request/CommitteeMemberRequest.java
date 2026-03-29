package com.jobfair.api.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CommitteeMemberRequest(
        @NotBlank(message = "Committee ID is required")
        String committeeId,

        @NotBlank(message = "Person ID is required")
        String personId,

        String role
) {
}
