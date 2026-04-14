package com.jobfair.shared.response;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Standard envelope returned by JSON API endpoints.")
public record ApiResponse<T>(
        @Schema(description = "Indicates whether the request completed successfully.", example = "true")
        boolean success,
        @Schema(description = "Human-readable outcome summary.", example = "Article fetched successfully")
        String message,
        @Schema(description = "Endpoint-specific payload. This is null for delete operations and some errors.")
        T data,
        @Schema(description = "Server-side response timestamp in ISO-8601 local date-time format.", example = "2026-04-14T21:30:00")
        LocalDateTime timestamp
) {

    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(true, message, data, LocalDateTime.now());
    }

    public static <T> ApiResponse<T> error(String message, T data) {
        return new ApiResponse<>(false, message, data, LocalDateTime.now());
    }
}
