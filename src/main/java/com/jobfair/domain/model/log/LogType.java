package com.jobfair.domain.model.log;

import lombok.Getter;

@Getter
public enum LogType {
    AUDIT("audit_logs"),
    MESSAGE("message_logs"),
    NOTIFICATION("notification_logs"),
    STATUS_HISTORY("status_histories");

    private final String collectionName;

    LogType(String collectionName) {
        this.collectionName = collectionName;
    }
}