package com.jobfair.api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jobfair.api.dto.request.LogRequest;
import com.jobfair.api.dto.response.LogResponse;
import com.jobfair.domain.service.LogService;
import com.jobfair.shared.constants.ApiPaths;
import com.jobfair.shared.docs.ApiResourceDocumentation;
import com.jobfair.shared.docs.DocParameter;
import com.jobfair.shared.docs.EndpointDocumentation;
import com.jobfair.shared.docs.ErrorDocProfile;
import com.jobfair.shared.response.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping(ApiPaths.LOGS)
@ApiResourceDocumentation(order = 30, singularName = "log", pluralName = "logs", sectionTitle = "Logs", snippetPrefix = "logs", sampleId = "log-1", description = "MongoDB logging endpoints for audit, message, notification, and status history logs.")
@Tag(name = "Logs", description = "MongoDB logging endpoints for audit, message, notification, and status history logs.")
@Validated
public class LogController {

    private final LogService logService;

    public LogController(LogService logService) {
        this.logService = logService;
    }

    @GetMapping
    @Operation(summary = "Get all Mongo log entries")
    @EndpointDocumentation(order = 100, snippetId = "logs-getall", displayName = "GET /logs", summary = "Get all log entries")
    public ResponseEntity<ApiResponse<List<LogResponse>>> getAll() {
        List<LogResponse> payload = logService.getAll();
        return ResponseEntity.ok(ApiResponse.success("Logs fetched successfully", payload));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Mongo log entry by ID")
    @EndpointDocumentation(order = 110, snippetId = "logs-getbyid", displayName = "GET /logs/{id}", summary = "Get log entry by ID", pathParameters = @DocParameter(name = "id", value = "log-1"), errorProfiles = ErrorDocProfile.NOT_FOUND)
    public ResponseEntity<ApiResponse<LogResponse>> getById(@PathVariable String id) {
        LogResponse payload = logService.getById(id);
        return ResponseEntity.ok(ApiResponse.success("Log fetched successfully", payload));
    }

    @PostMapping
    @Operation(summary = "Create a Mongo log entry")
    @EndpointDocumentation(order = 120, snippetId = "logs-create", displayName = "POST /logs", summary = "Create a log entry")
    public ResponseEntity<ApiResponse<LogResponse>> create(@Valid @RequestBody LogRequest request) {
        LogResponse payload = logService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Log created successfully", payload));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Replace a Mongo log entry")
    @EndpointDocumentation(order = 130, snippetId = "logs-update", displayName = "PUT /logs/{id}", summary = "Replace a log entry", pathParameters = @DocParameter(name = "id", value = "log-1"), errorProfiles = ErrorDocProfile.NOT_FOUND)
    public ResponseEntity<ApiResponse<LogResponse>> update(@PathVariable String id, @Valid @RequestBody LogRequest request) {
        LogResponse payload = logService.update(id, request);
        return ResponseEntity.ok(ApiResponse.success("Log updated successfully", payload));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a Mongo log entry")
    @EndpointDocumentation(order = 140, snippetId = "logs-delete", displayName = "DELETE /logs/{id}", summary = "Delete a log entry", pathParameters = @DocParameter(name = "id", value = "log-1"), errorProfiles = ErrorDocProfile.NOT_FOUND)
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable String id) {
        logService.delete(id);
        return ResponseEntity.ok(ApiResponse.success("Log deleted successfully", null));
    }
}