package com.jobfair.api.dto.request;

import com.jobfair.domain.model.enums.MediaKind;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record MediaOutletRequest(
        @NotNull(message = "Name is required")
        @Size(max = 200, message = "Name must be at most 200 characters")
        String name,

        @NotNull(message = "Slug is required")
        @Size(max = 150, message = "Slug must be at most 150 characters")
        String slug,

        @Size(max = 250, message = "Website must be at most 250 characters")
        String website,

        @NotNull(message = "Kind is required")
        MediaKind kind
) {
}
