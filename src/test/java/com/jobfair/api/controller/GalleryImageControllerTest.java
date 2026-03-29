package com.jobfair.api.controller;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.jobfair.api.dto.response.GalleryImageResponse;
import com.jobfair.domain.service.GalleryImageService;
import com.jobfair.shared.response.ApiResponse;

@ExtendWith(MockitoExtension.class)
class GalleryImageControllerTest {

    @Mock
    private GalleryImageService galleryImageService;

    private GalleryImageController controller;

    @BeforeEach
    void setUp() {
        controller = new GalleryImageController(galleryImageService);
    }

    @Test
    void organizationDelegatesToService() {
        when(galleryImageService.getByOrganizationId("o1")).thenReturn(List.of());

        ResponseEntity<ApiResponse<List<GalleryImageResponse>>> response = controller.organization("o1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(galleryImageService).getByOrganizationId("o1");
    }

    @Test
    void mediaDelegatesToService() {
        when(galleryImageService.getByMediaId("m1")).thenReturn(List.of());

        ResponseEntity<ApiResponse<List<GalleryImageResponse>>> response = controller.media("m1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(galleryImageService).getByMediaId("m1");
    }

    @Test
    void filterConvertsBlankStringsToNull() {
        controller.filter("  ", " ", "   ", 2, 7);

        verify(galleryImageService).filter(null, null, null, 2, 7);
    }
}
