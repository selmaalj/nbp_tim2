package com.jobfair.api.controller;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.jobfair.api.dto.response.StatBoardResponse;
import com.jobfair.domain.service.StatBoardService;
import com.jobfair.shared.response.ApiResponse;

@ExtendWith(MockitoExtension.class)
class StatBoardControllerTest {

    @Mock
    private StatBoardService statBoardService;

    private StatBoardController controller;

    @BeforeEach
    void setUp() {
        controller = new StatBoardController(statBoardService);
    }

    @Test
    void slugReturnsOkResponse() {
        when(statBoardService.getBySlug("stats")).thenReturn(null);

        ResponseEntity<ApiResponse<StatBoardResponse>> response = controller.slug("stats");
        ApiResponse<StatBoardResponse> body = response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(body);
        assertEquals("Stat board by slug", body.message());
        verify(statBoardService).getBySlug("stats");
    }

    @Test
    void yearDelegatesToService() {
        when(statBoardService.getByYear(2026)).thenReturn(List.of());

        ResponseEntity<ApiResponse<List<StatBoardResponse>>> response = controller.year(2026);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(statBoardService).getByYear(2026);
    }

    @Test
    void searchTrimsInput() {
        controller.search("  annual  ");

        verify(statBoardService).search("annual");
    }

    @Test
    void filterConvertsBlankQueryToNull() {
        controller.filter("   ", 2026);

        verify(statBoardService).filter(null, 2026);
    }
}
