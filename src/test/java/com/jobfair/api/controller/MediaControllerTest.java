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

import com.jobfair.api.dto.response.MediaResponse;
import com.jobfair.domain.service.MediaService;
import com.jobfair.shared.response.ApiResponse;

@ExtendWith(MockitoExtension.class)
class MediaControllerTest {

    @Mock
    private MediaService mediaService;

    private MediaController controller;

    @BeforeEach
    void setUp() {
        controller = new MediaController(mediaService);
    }

    @Test
    void urlReturnsOkResponse() {
        when(mediaService.getByUrl("u")).thenReturn(null);

        ResponseEntity<ApiResponse<MediaResponse>> response = controller.url("u");
        ApiResponse<MediaResponse> body = response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(body);
        assertEquals("Media by url", body.message());
        verify(mediaService).getByUrl("u");
    }

    @Test
    void mimeDelegatesToService() {
        when(mediaService.getByMimeType("image/png")).thenReturn(List.of());

        ResponseEntity<ApiResponse<List<MediaResponse>>> response = controller.mime("image/png");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(mediaService).getByMimeType("image/png");
    }

    @Test
    void searchTrimsInput() {
        controller.search("  pdf  ");

        verify(mediaService).search("pdf");
    }

    @Test
    void filterConvertsBlankStringsToNull() {
        controller.filter("   ", "   ", 10, 20);

        verify(mediaService).filter(null, null, 10, 20);
    }
}
