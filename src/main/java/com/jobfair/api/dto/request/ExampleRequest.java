package com.jobfair.api.dto.request;

import jakarta.validation.constraints.Size;

public record ExampleRequest(
        @Size(max = 150, message = "Title must be at most 150 characters")
        String title,

        @Size(max = 2000, message = "Description must be at most 2000 characters")
        String description
) {
}
