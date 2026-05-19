package com.jobfair.domain.repository.log;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.jobfair.domain.model.log.MessageLog;

public interface MessageLogRepository extends MongoRepository<MessageLog, String> {

    List<MessageLog> findByOraclePersonIdOrderByCreatedAtDesc(String oraclePersonId);
}
