package com.jobfair.domain.model.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Participation sponsorship tier for media outlets.")
public enum MediaTier {
    GOLD,
    SILVER,
    BRONZE,
    PARTNER
}
