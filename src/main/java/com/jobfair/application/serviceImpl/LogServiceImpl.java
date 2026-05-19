package com.jobfair.application.serviceImpl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jobfair.api.dto.request.LogRequest;
import com.jobfair.api.dto.response.LogResponse;
import com.jobfair.domain.model.log.AuditLog;
import com.jobfair.domain.model.log.BaseLogDocument;
import com.jobfair.domain.model.log.LogType;
import com.jobfair.domain.model.log.MessageLog;
import com.jobfair.domain.model.log.NotificationLog;
import com.jobfair.domain.model.log.StatusHistory;
import com.jobfair.domain.repository.log.AuditLogRepository;
import com.jobfair.domain.repository.log.MessageLogRepository;
import com.jobfair.domain.repository.log.NotificationLogRepository;
import com.jobfair.domain.repository.log.StatusHistoryRepository;
import com.jobfair.domain.service.LogService;
import com.jobfair.shared.exception.ResourceNotFoundException;

@Service
@Transactional
public class LogServiceImpl implements LogService {

    private final AuditLogRepository auditLogRepository;
    private final MessageLogRepository messageLogRepository;
    private final NotificationLogRepository notificationLogRepository;
    private final StatusHistoryRepository statusHistoryRepository;

    public LogServiceImpl(
            AuditLogRepository auditLogRepository,
            MessageLogRepository messageLogRepository,
            NotificationLogRepository notificationLogRepository,
            StatusHistoryRepository statusHistoryRepository
    ) {
        this.auditLogRepository = auditLogRepository;
        this.messageLogRepository = messageLogRepository;
        this.notificationLogRepository = notificationLogRepository;
        this.statusHistoryRepository = statusHistoryRepository;
    }

    @Override
    public LogResponse create(LogRequest request) {
        BaseLogDocument document = createDocument(request.type());
        applyRequest(document, request, true);
        BaseLogDocument saved = save(document);
        return toResponse(saved, request.type());
    }

    @Override
    @Transactional(readOnly = true)
    public List<LogResponse> getAll() {
        List<LogResponse> payload = new ArrayList<>();
        auditLogRepository.findAll().forEach(document -> payload.add(toResponse(document, LogType.AUDIT)));
        messageLogRepository.findAll().forEach(document -> payload.add(toResponse(document, LogType.MESSAGE)));
        notificationLogRepository.findAll().forEach(document -> payload.add(toResponse(document, LogType.NOTIFICATION)));
        statusHistoryRepository.findAll().forEach(document -> payload.add(toResponse(document, LogType.STATUS_HISTORY)));
        return payload.stream()
                .sorted((left, right) -> {
                    if (left.createdAt() == null && right.createdAt() == null) {
                        return 0;
                    }
                    if (left.createdAt() == null) {
                        return 1;
                    }
                    if (right.createdAt() == null) {
                        return -1;
                    }
                    return right.createdAt().compareTo(left.createdAt());
                })
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public LogResponse getById(String id) {
        ResolvedLog resolved = resolveById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Log not found with id: " + id));
        return toResponse(resolved.document(), resolved.type());
    }

    @Override
    public LogResponse update(String id, LogRequest request) {
        ResolvedLog resolved = resolveById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Log not found with id: " + id));

        if (request.type() != resolved.type()) {
            throw new IllegalArgumentException("Log type cannot be changed once stored");
        }

        applyRequest(resolved.document(), request, false);
        BaseLogDocument saved = save(resolved.document());
        return toResponse(saved, resolved.type());
    }

    @Override
    public void delete(String id) {
        ResolvedLog resolved = resolveById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Log not found with id: " + id));

        delete(resolved.document(), resolved.type());
    }

    private void applyRequest(BaseLogDocument target, LogRequest request, boolean createMode) {
        target.setOracleUserId(request.oracleUserId());

        if (request.title() != null && !request.title().isBlank()) {
            target.setTitle(request.title().trim());
        } else if (createMode) {
            throw new IllegalArgumentException("Title is required");
        }

        if (request.message() != null && !request.message().isBlank()) {
            target.setMessage(request.message().trim());
        } else if (createMode) {
            throw new IllegalArgumentException("Message is required");
        }

        if (request.status() != null) {
            target.setStatus(request.status().trim());
        }

        if (request.details() != null) {
            target.setDetails(new LinkedHashMap<>(request.details()));
        } else if (createMode && target.getDetails() == null) {
            target.setDetails(new LinkedHashMap<>());
        }
    }

    private BaseLogDocument createDocument(LogType type) {
        if (type == null) {
            throw new IllegalArgumentException("Log type is required");
        }

        return switch (type) {
            case AUDIT -> new AuditLog();
            case MESSAGE -> new MessageLog();
            case NOTIFICATION -> new NotificationLog();
            case STATUS_HISTORY -> new StatusHistory();
        };
    }

    private BaseLogDocument save(BaseLogDocument document) {
        if (document instanceof AuditLog auditLog) {
            return auditLogRepository.save(auditLog);
        }
        if (document instanceof MessageLog messageLog) {
            return messageLogRepository.save(messageLog);
        }
        if (document instanceof NotificationLog notificationLog) {
            return notificationLogRepository.save(notificationLog);
        }
        if (document instanceof StatusHistory statusHistory) {
            return statusHistoryRepository.save(statusHistory);
        }
        if (document != null) {
            throw new IllegalStateException("Unsupported log document type: " + document.getClass().getName());
        }
        throw new IllegalStateException("Log document cannot be null");
    }

    private void delete(BaseLogDocument document, LogType type) {
        if (type == LogType.AUDIT && document instanceof AuditLog auditLog) {
            auditLogRepository.delete(auditLog);
            return;
        }
        if (type == LogType.MESSAGE && document instanceof MessageLog messageLog) {
            messageLogRepository.delete(messageLog);
            return;
        }
        if (type == LogType.NOTIFICATION && document instanceof NotificationLog notificationLog) {
            notificationLogRepository.delete(notificationLog);
            return;
        }
        if (type == LogType.STATUS_HISTORY && document instanceof StatusHistory statusHistory) {
            statusHistoryRepository.delete(statusHistory);
            return;
        }
        if (document != null) {
            throw new IllegalStateException("Unsupported log document type: " + document.getClass().getName());
        }
        throw new IllegalStateException("Log document cannot be null");
    }

    private LogResponse toResponse(BaseLogDocument document, LogType type) {
        return new LogResponse(
                document.getId(),
                type,
                document.getOracleUserId(),
                document.getTitle(),
                document.getMessage(),
                document.getStatus(),
                document.safeDetails(),
                document.getCreatedAt(),
                document.getUpdatedAt()
        );
    }

    private java.util.Optional<ResolvedLog> resolveById(String id) {
        java.util.Optional<AuditLog> auditLog = auditLogRepository.findById(id);
        if (auditLog.isPresent()) {
            return java.util.Optional.of(new ResolvedLog(auditLog.get(), LogType.AUDIT));
        }

        java.util.Optional<MessageLog> messageLog = messageLogRepository.findById(id);
        if (messageLog.isPresent()) {
            return java.util.Optional.of(new ResolvedLog(messageLog.get(), LogType.MESSAGE));
        }

        java.util.Optional<NotificationLog> notificationLog = notificationLogRepository.findById(id);
        if (notificationLog.isPresent()) {
            return java.util.Optional.of(new ResolvedLog(notificationLog.get(), LogType.NOTIFICATION));
        }

        java.util.Optional<StatusHistory> statusHistory = statusHistoryRepository.findById(id);
        if (statusHistory.isPresent()) {
            return java.util.Optional.of(new ResolvedLog(statusHistory.get(), LogType.STATUS_HISTORY));
        }

        return java.util.Optional.empty();
    }

    private record ResolvedLog(BaseLogDocument document, LogType type) {
    }
}