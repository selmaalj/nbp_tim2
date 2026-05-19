package com.jobfair.domain.model.log;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "audit_logs")
public class AuditLog extends BaseLogDocument {
}