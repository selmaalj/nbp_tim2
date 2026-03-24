package com.jobfair.api.dto.response;

import com.jobfair.domain.model.enums.MediaKind;
import java.time.LocalDateTime;

public record MediaOutletResponse(
        String id,
        String name,
        String slug,
        String website,
        MediaKind kind,
        LocalDateTime createdAt
) {
}
