package com.jobfair.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record GalleryImageRequest(
        @NotBlank(message = "Organization ID is required")
        String organizationId,

        @NotBlank(message = "Media ID is required")
        String mediaId,

        @NotNull(message = "Sort order is required")
        Integer sort,

        String altText
) {
}
