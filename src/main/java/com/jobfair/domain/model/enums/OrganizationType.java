package com.jobfair.domain.model.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Organization classification.")
public enum OrganizationType {
    COMPANY,
    INSTITUTION,
    ASSOCIATION,
    NGO
}
