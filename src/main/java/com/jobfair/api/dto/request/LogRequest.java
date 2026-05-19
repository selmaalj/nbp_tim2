package com.jobfair.api.dto.request;

import java.util.Map;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import com.jobfair.domain.model.log.LogType;

public record LogRequest(
        @NotNull(message = "Log type is required")
        LogType type,

        @NotNull(message = "Oracle user ID is required")
        Integer oracleUserId,

        @NotBlank(message = "Title is required")
        String title,

        @NotBlank(message = "Message is required")
        String message,

        String status,

        Map<String, Object> details
) {
}