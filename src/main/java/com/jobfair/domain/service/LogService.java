package com.jobfair.domain.service;

import java.util.List;

import com.jobfair.api.dto.request.LogRequest;
import com.jobfair.api.dto.response.LogResponse;

public interface LogService {

    LogResponse create(LogRequest request);

    List<LogResponse> getAll();

    LogResponse getById(String id);

    LogResponse update(String id, LogRequest request);

    void delete(String id);

    List<LogResponse> getByPersonId(String personId);

    List<LogResponse> getStatusHistoryByPersonId(String personId);

    List<LogResponse> getNotificationsByPersonId(String personId);

    void auditPersonAction(String personId, String title, String message, String status, java.util.Map<String, Object> details);
}
