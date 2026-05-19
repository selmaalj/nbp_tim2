package com.jobfair.application.serviceImpl;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.jobfair.api.dto.request.LogRequest;
import com.jobfair.api.dto.response.LogResponse;
import com.jobfair.domain.model.log.AuditLog;
import com.jobfair.domain.model.log.LogType;
import com.jobfair.domain.model.log.MessageLog;
import com.jobfair.domain.model.log.NotificationLog;
import com.jobfair.domain.repository.PersonRepository;
import com.jobfair.domain.repository.log.AuditLogRepository;
import com.jobfair.domain.repository.log.MessageLogRepository;
import com.jobfair.domain.repository.log.NotificationLogRepository;
import com.jobfair.domain.repository.log.StatusHistoryRepository;
import com.jobfair.shared.exception.ResourceNotFoundException;

@ExtendWith(MockitoExtension.class)
class LogServiceImplTest {

    @Mock
    private AuditLogRepository auditLogRepository;

    @Mock
    private MessageLogRepository messageLogRepository;

    @Mock
    private NotificationLogRepository notificationLogRepository;

    @Mock
    private StatusHistoryRepository statusHistoryRepository;

    @Mock
    private PersonRepository personRepository;

    private LogServiceImpl service;

    @BeforeEach
    @SuppressWarnings("unused")
    void setUp() {
        service = new LogServiceImpl(auditLogRepository, messageLogRepository, notificationLogRepository, statusHistoryRepository, personRepository);
    }

    @Test
    void createStoresAuditLogInAuditCollection() {
        LogRequest request = new LogRequest(LogType.AUDIT, "person-7", "Created", "Record created", "SUCCESS", Map.of("entity", "person"));
        when(personRepository.existsById("person-7")).thenReturn(true);
        when(auditLogRepository.save(any(AuditLog.class))).thenAnswer(invocation -> invocation.getArgument(0));

        LogResponse response = service.create(request);

        assertEquals(LogType.AUDIT, response.type());
        assertEquals("person-7", response.oraclePersonId());
        verify(auditLogRepository).save(any(AuditLog.class));
    }

    @Test
    void createStoresNotificationLogInNotificationCollection() {
        LogRequest request = new LogRequest(LogType.NOTIFICATION, "person-12", "Notify", "Sent", "DONE", Map.of());
        when(personRepository.existsById("person-12")).thenReturn(true);
        when(notificationLogRepository.save(any(NotificationLog.class))).thenAnswer(invocation -> invocation.getArgument(0));

        LogResponse response = service.create(request);

        assertEquals(LogType.NOTIFICATION, response.type());
        verify(notificationLogRepository).save(any(NotificationLog.class));
    }

    @Test
    void getAllCombinesAllCollections() {
        AuditLog auditLog = new AuditLog();
        auditLog.setId("1");
        auditLog.setOraclePersonId("person-1");
        auditLog.setCreatedAt(Instant.parse("2024-01-01T00:00:00Z"));
        MessageLog messageLog = new MessageLog();
        messageLog.setId("2");
        messageLog.setOraclePersonId("person-1");
        messageLog.setCreatedAt(Instant.parse("2024-01-02T00:00:00Z"));

        when(auditLogRepository.findAll()).thenReturn(List.of(auditLog));
        when(messageLogRepository.findAll()).thenReturn(List.of(messageLog));
        when(notificationLogRepository.findAll()).thenReturn(List.of());
        when(statusHistoryRepository.findAll()).thenReturn(List.of());

        List<LogResponse> result = service.getAll();

        assertEquals(2, result.size());
        assertEquals("2", result.get(0).id());
        assertEquals("1", result.get(1).id());
    }

    @Test
    void updateRejectsTypeChange() {
        AuditLog auditLog = new AuditLog();
        auditLog.setId("1");
        when(auditLogRepository.findById("1")).thenReturn(Optional.of(auditLog));
        when(personRepository.existsById("person-1")).thenReturn(true);

        LogRequest request = new LogRequest(LogType.MESSAGE, "person-1", "A", "B", null, Map.of());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> service.update("1", request));
        assertEquals("Log type cannot be changed once stored", exception.getMessage());
    }

    @Test
    void getByIdThrowsWhenMissing() {
        when(auditLogRepository.findById("missing")).thenReturn(Optional.empty());
        when(messageLogRepository.findById("missing")).thenReturn(Optional.empty());
        when(notificationLogRepository.findById("missing")).thenReturn(Optional.empty());
        when(statusHistoryRepository.findById("missing")).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> service.getById("missing"));
        assertEquals("Log not found with id: missing", exception.getMessage());
    }

    @Test
    void createRejectsMissingOraclePerson() {
        LogRequest request = new LogRequest(LogType.AUDIT, "missing-person", "Created", "Record created", "SUCCESS", Map.of());
        when(personRepository.existsById("missing-person")).thenReturn(false);

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> service.create(request));

        assertEquals("Person not found with id: missing-person", exception.getMessage());
    }

    @Test
    void getByPersonIdCombinesPersonLogs() {
        AuditLog auditLog = new AuditLog();
        auditLog.setId("audit-1");
        auditLog.setOraclePersonId("person-1");
        auditLog.setCreatedAt(Instant.parse("2024-01-01T00:00:00Z"));
        MessageLog messageLog = new MessageLog();
        messageLog.setId("message-1");
        messageLog.setOraclePersonId("person-1");
        messageLog.setCreatedAt(Instant.parse("2024-01-02T00:00:00Z"));

        when(personRepository.existsById("person-1")).thenReturn(true);
        when(auditLogRepository.findByOraclePersonIdOrderByCreatedAtDesc("person-1")).thenReturn(List.of(auditLog));
        when(messageLogRepository.findByOraclePersonIdOrderByCreatedAtDesc("person-1")).thenReturn(List.of(messageLog));
        when(notificationLogRepository.findByOraclePersonIdOrderByCreatedAtDesc("person-1")).thenReturn(List.of());
        when(statusHistoryRepository.findByOraclePersonIdOrderByCreatedAtDesc("person-1")).thenReturn(List.of());

        List<LogResponse> result = service.getByPersonId("person-1");

        assertEquals(2, result.size());
        assertEquals("message-1", result.get(0).id());
        assertEquals("audit-1", result.get(1).id());
    }
}
