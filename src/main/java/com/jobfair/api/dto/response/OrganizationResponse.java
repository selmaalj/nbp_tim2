package com.jobfair.api.dto.response;

import com.jobfair.domain.model.enums.OrganizationType;
import java.time.LocalDateTime;

public record OrganizationResponse(
        String id,
        OrganizationType type,
        String name,
        String slug,
        String website,
        String description,
        LocalDateTime createdAt
) {
}
