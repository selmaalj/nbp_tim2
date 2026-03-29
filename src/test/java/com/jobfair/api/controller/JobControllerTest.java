package com.jobfair.api.controller;

import java.time.LocalDateTime;
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

import com.jobfair.api.dto.response.JobResponse;
import com.jobfair.domain.service.JobService;
import com.jobfair.shared.response.ApiResponse;

@ExtendWith(MockitoExtension.class)
class JobControllerTest {

    @Mock
    private JobService jobService;

    private JobController controller;

    @BeforeEach
    @SuppressWarnings("unused")
    void setUp() {
        controller = new JobController(jobService);
    }

    @Test
    void slugReturnsOkResponse() {
        JobResponse dto = new JobResponse("1", "Java Dev", "java-dev", "desc", null, null, null, null, null);
        when(jobService.getBySlug("java-dev")).thenReturn(dto);

        ResponseEntity<ApiResponse<JobResponse>> response = controller.slug("java-dev");
        ApiResponse<JobResponse> body = response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(body);
        assertEquals(dto, body.data());
    }

    @Test
    void searchTrimsInput() {
        controller.search("  java  ");

        verify(jobService).search("java");
    }

    @Test
    void filterConvertsBlankQueryToNull() {
        LocalDateTime postedAfter = LocalDateTime.of(2026, 1, 1, 0, 0);
        LocalDateTime expiresBefore = LocalDateTime.of(2026, 6, 1, 0, 0);

        controller.filter("   ", postedAfter, expiresBefore, true);

        verify(jobService).filter(null, postedAfter, expiresBefore, true);
    }

    @Test
    void activeReturnsPayload() {
        JobResponse dto = new JobResponse("1", "Java Dev", "java-dev", "desc", null, null, null, null, null);
        when(jobService.getActive()).thenReturn(List.of(dto));

        ResponseEntity<ApiResponse<List<JobResponse>>> response = controller.active();
        ApiResponse<List<JobResponse>> body = response.getBody();

        assertNotNull(body);
        assertNotNull(body.data());
        assertEquals(1, body.data().size());
        assertEquals("Active jobs", body.message());
    }
}
