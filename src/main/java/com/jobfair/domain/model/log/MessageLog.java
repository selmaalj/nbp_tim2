package com.jobfair.domain.model.log;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "message_logs")
public class MessageLog extends BaseLogDocument {
}