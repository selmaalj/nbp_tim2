package com.jobfair.domain.repository.log;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.jobfair.domain.model.log.AuditLog;

public interface AuditLogRepository extends MongoRepository<AuditLog, String> {
}