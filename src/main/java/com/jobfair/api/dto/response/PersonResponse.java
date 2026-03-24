package com.jobfair.api.dto.response;

import java.time.LocalDateTime;

public record PersonResponse(
        String id,
        String firstName,
        String lastName,
        String email,
        String phone,
        String position,
        LocalDateTime createdAt
) {
}
