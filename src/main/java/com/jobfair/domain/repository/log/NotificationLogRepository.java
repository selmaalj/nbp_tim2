package com.jobfair.domain.repository.log;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.jobfair.domain.model.log.NotificationLog;

public interface NotificationLogRepository extends MongoRepository<NotificationLog, String> {
}