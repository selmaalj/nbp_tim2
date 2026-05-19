package com.jobfair.domain.repository.log;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.jobfair.domain.model.log.StatusHistory;

public interface StatusHistoryRepository extends MongoRepository<StatusHistory, String> {
}