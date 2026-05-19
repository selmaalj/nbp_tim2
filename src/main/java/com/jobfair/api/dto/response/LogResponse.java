package com.jobfair.api.dto.response;

import java.time.Instant;
import java.util.Map;

import com.jobfair.domain.model.log.LogType;

public record LogResponse(
        String id,
        LogType type,
        String oraclePersonId,
        String title,
        String message,
        String status,
        Map<String, Object> details,
        Instant createdAt,
        Instant updatedAt
) {
}
