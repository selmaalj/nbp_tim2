package com.jobfair.domain.repository.log;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.jobfair.domain.model.log.StatusHistory;

public interface StatusHistoryRepository extends MongoRepository<StatusHistory, String> {

    List<StatusHistory> findByOraclePersonIdOrderByCreatedAtDesc(String oraclePersonId);
}
