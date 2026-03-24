package com.jobfair.api.dto.response;

import java.time.LocalDateTime;

public record ExampleResponse(
        Long id,
        String title,
        String description,
        LocalDateTime createdAt
) {
}
