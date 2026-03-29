package com.jobfair.api.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record StatBoardRequest(
        @NotNull(message = "Slug is required")
        @Size(max = 150, message = "Slug must be at most 150 characters")
        String slug,

        @NotNull(message = "Title is required")
        @Size(max = 200, message = "Title must be at most 200 characters")
        String title,

        Integer year,

        @Size(max = 1000, message = "Description must be at most 1000 characters")
        String description
) {
}
