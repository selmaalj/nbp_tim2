package com.jobfair.shared.response;

import java.util.Map;

public record ValidationErrorResponse(
        String message,
        Map<String, String> errors
) {
}
