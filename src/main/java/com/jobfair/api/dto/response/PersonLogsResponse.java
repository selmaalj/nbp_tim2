package com.jobfair.api.dto.response;

import java.util.List;

public record PersonLogsResponse(
        PersonResponse person,
        List<LogResponse> items
) {
}
