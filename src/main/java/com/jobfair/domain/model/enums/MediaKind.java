package com.jobfair.domain.model.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Media outlet kind.")
public enum MediaKind {
    PRINT,
    ONLINE,
    TELEVISION,
    RADIO
}
