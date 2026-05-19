package com.jobfair.api.dto.request;

import java.util.Map;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import com.jobfair.domain.model.log.LogType;

public record LogRequest(
        @NotNull(message = "Log type is required")
        LogType type,

        @NotBlank(message = "Oracle person ID is required")
        String oraclePersonId,

        @NotBlank(message = "Title is required")
        String title,

        @NotBlank(message = "Message is required")
        String message,

        String status,

        Map<String, Object> details
) {
}
