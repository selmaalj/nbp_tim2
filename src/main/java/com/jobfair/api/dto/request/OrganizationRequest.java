package com.jobfair.api.dto.request;

import com.jobfair.domain.model.enums.OrganizationType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record OrganizationRequest(
        @NotNull(message = "Type is required")
        OrganizationType type,

        @NotNull(message = "Name is required")
        @Size(max = 200, message = "Name must be at most 200 characters")
        String name,

        @NotNull(message = "Slug is required")
        @Size(max = 150, message = "Slug must be at most 150 characters")
        String slug,

        @Size(max = 250, message = "Website must be at most 250 characters")
        String website,

        @Size(max = 2000, message = "Description must be at most 2000 characters")
        String description
) {
}
