package com.jobfair.api.dto.response;

import java.time.LocalDateTime;

public record StatBoardResponse(
        String id,
        String slug,
        String title,
        Integer year,
        String description,
        LocalDateTime createdAt
) {
}
