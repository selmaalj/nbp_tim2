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

import com.jobfair.api.dto.response.CommitteeResponse;
import com.jobfair.domain.service.CommitteeService;
import com.jobfair.shared.response.ApiResponse;

@ExtendWith(MockitoExtension.class)
class CommitteeControllerTest {

    @Mock
    private CommitteeService committeeService;

    private CommitteeController controller;

    @BeforeEach
    void setUp() {
        controller = new CommitteeController(committeeService);
    }

    @Test
    void yearDelegatesToService() {
        when(committeeService.getByYear(2026)).thenReturn(null);

        ResponseEntity<ApiResponse<CommitteeResponse>> response = controller.year(2026);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(committeeService).getByYear(2026);
    }

    @Test
    void latestReturnsMessage() {
        when(committeeService.getLatest()).thenReturn(null);

        ResponseEntity<ApiResponse<CommitteeResponse>> response = controller.latest();
        ApiResponse<CommitteeResponse> body = response.getBody();

        assertNotNull(body);
        assertEquals("Latest committee", body.message());
        verify(committeeService).getLatest();
    }

    @Test
    void searchTrimsInput() {
        controller.search("  board  ");

        verify(committeeService).search("board");
    }

    @Test
    void filterConvertsBlankQueryToNull() {
        controller.filter("   ", 2020, 2026);

        verify(committeeService).filter(null, 2020, 2026);
    }
}
