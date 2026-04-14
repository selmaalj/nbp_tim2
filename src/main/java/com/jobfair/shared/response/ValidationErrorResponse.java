package com.jobfair.shared.response;

import java.util.Map;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Detailed validation failure payload.")
public record ValidationErrorResponse(
        @Schema(description = "Validation summary.", example = "Validation failed")
        String message,
        @Schema(description = "Validation errors keyed by field or parameter name.")
        Map<String, String> errors
) {
}
