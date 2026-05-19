package com.jobfair.api.controller;

import java.time.Instant;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.jobfair.api.dto.request.LogRequest;
import com.jobfair.api.dto.response.LogResponse;
import com.jobfair.domain.model.log.LogType;
import com.jobfair.domain.service.LogService;
import com.jobfair.shared.response.ApiResponse;

class LogControllerTest {

    private final LogService logService = Mockito.mock(LogService.class);
    private final LogController controller = new LogController(logService);

    @Test
    void getAllReturnsPayload() {
        LogResponse response = new LogResponse("1", LogType.AUDIT, 100, "Created", "User created", "SUCCESS", Map.of("entity", "person"), Instant.now(), Instant.now());
        when(logService.getAll()).thenReturn(List.of(response));

        ResponseEntity<ApiResponse<List<LogResponse>>> result = controller.getAll();

        assertEquals(HttpStatus.OK, result.getStatusCode());
        ApiResponse<List<LogResponse>> body = result.getBody();
        assertNotNull(body);
        assertEquals(1, body.data().size());
        verify(logService).getAll();
    }

    @Test
    void getByIdReturnsPayload() {
        LogResponse response = new LogResponse("1", LogType.MESSAGE, 100, "Message", "Sent", "DELIVERED", Map.of(), Instant.now(), Instant.now());
        when(logService.getById("1")).thenReturn(response);

        ResponseEntity<ApiResponse<LogResponse>> result = controller.getById("1");

        assertEquals(HttpStatus.OK, result.getStatusCode());
        ApiResponse<LogResponse> body = result.getBody();
        assertNotNull(body);
        assertEquals(response, body.data());
    }

    @Test
    void createReturnsCreated() {
        LogRequest request = new LogRequest(LogType.NOTIFICATION, 11, "Notify", "Sent", "DONE", Map.of("channel", "email"));
        LogResponse response = new LogResponse("2", LogType.NOTIFICATION, 11, "Notify", "Sent", "DONE", Map.of("channel", "email"), Instant.now(), Instant.now());
        when(logService.create(request)).thenReturn(response);

        ResponseEntity<ApiResponse<LogResponse>> result = controller.create(request);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        ApiResponse<LogResponse> body = result.getBody();
        assertNotNull(body);
        assertEquals(response, body.data());
    }

    @Test
    void updateReturnsPayload() {
        LogRequest request = new LogRequest(LogType.STATUS_HISTORY, 22, "Status", "Moved", "ARCHIVED", Map.of());
        LogResponse response = new LogResponse("3", LogType.STATUS_HISTORY, 22, "Status", "Moved", "ARCHIVED", Map.of(), Instant.now(), Instant.now());
        when(logService.update("3", request)).thenReturn(response);

        ResponseEntity<ApiResponse<LogResponse>> result = controller.update("3", request);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        ApiResponse<LogResponse> body = result.getBody();
        assertNotNull(body);
        assertEquals(response, body.data());
    }

    @Test
    void deleteReturnsSuccessEnvelope() {
        ResponseEntity<ApiResponse<Void>> result = controller.delete("99");

        assertEquals(HttpStatus.OK, result.getStatusCode());
        ApiResponse<Void> body = result.getBody();
        assertNotNull(body);
        assertEquals("Log deleted successfully", body.message());
        verify(logService).delete("99");
    }
}