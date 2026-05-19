package com.jobfair.infrastructure.mongo;

import java.time.Instant;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.DockerClientFactory;
import org.testcontainers.containers.MongoDBContainer;

import com.jobfair.domain.model.log.AuditLog;
import com.jobfair.domain.model.log.StatusHistory;
import com.jobfair.domain.repository.log.AuditLogRepository;
import com.jobfair.domain.repository.log.StatusHistoryRepository;

@DataMongoTest
class LogMongoRepositoryTest {

    private static MongoDBContainer mongo;

    @DynamicPropertySource
    @SuppressWarnings("unused")
    static void mongoProperties(DynamicPropertyRegistry registry) {
        Assumptions.assumeTrue(DockerClientFactory.instance().isDockerAvailable(), "Docker is required for Mongo integration tests");
        mongo = new MongoDBContainer("mongo:7.0.12");
        mongo.start();
        registry.add("spring.data.mongodb.uri", () -> mongo.getReplicaSetUrl());
    }

    @Autowired
    private AuditLogRepository auditLogRepository;

    @Autowired
    private StatusHistoryRepository statusHistoryRepository;

    @Test
    void auditLogIsPersistedAndReadBack() {
        AuditLog document = new AuditLog();
        document.setOracleUserId(55);
        document.setTitle("Created");
        document.setMessage("Saved person");
        document.setStatus("SUCCESS");
        document.setDetails(Map.of("entity", "person"));
        document.setCreatedAt(Instant.now());

        AuditLog saved = auditLogRepository.save(document);
        AuditLog found = auditLogRepository.findById(saved.getId()).orElseThrow();

        assertEquals(55, found.getOracleUserId());
        assertEquals("Created", found.getTitle());
        assertEquals("person", found.safeDetails().get("entity"));
    }

    @Test
    void statusHistoryIsPersistedAndReadBack() {
        StatusHistory document = new StatusHistory();
        document.setOracleUserId(88);
        document.setTitle("Status changed");
        document.setMessage("Moved to approved");
        document.setStatus("APPROVED");
        document.setDetails(Map.of("from", "PENDING", "to", "APPROVED"));

        StatusHistory saved = statusHistoryRepository.save(document);
        StatusHistory found = statusHistoryRepository.findById(saved.getId()).orElseThrow();

        assertEquals(88, found.getOracleUserId());
        assertEquals("APPROVED", found.getStatus());
        assertEquals("APPROVED", found.safeDetails().get("to"));
    }
}