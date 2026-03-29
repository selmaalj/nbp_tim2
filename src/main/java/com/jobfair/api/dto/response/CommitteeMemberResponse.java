package com.jobfair.api.dto.response;

import java.time.LocalDateTime;

public record CommitteeMemberResponse(
        String id,
        String committeeId,
        String personId,
        String role,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
