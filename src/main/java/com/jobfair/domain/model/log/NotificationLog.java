package com.jobfair.domain.model.log;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "notification_logs")
public class NotificationLog extends BaseLogDocument {
}