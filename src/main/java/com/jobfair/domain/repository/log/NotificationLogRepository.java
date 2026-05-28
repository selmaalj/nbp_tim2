package com.jobfair.domain.repository.log;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.jobfair.domain.model.log.NotificationLog;

public interface NotificationLogRepository extends MongoRepository<NotificationLog, String> {

    List<NotificationLog> findByOraclePersonIdOrderByCreatedAtDesc(String oraclePersonId);
}
