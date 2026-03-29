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

import com.jobfair.api.dto.response.MediaParticipationResponse;
import com.jobfair.domain.model.enums.MediaTier;
import com.jobfair.domain.service.MediaParticipationService;
import com.jobfair.shared.response.ApiResponse;

@ExtendWith(MockitoExtension.class)
class MediaParticipationControllerTest {

    @Mock
    private MediaParticipationService mediaParticipationService;

    private MediaParticipationController controller;

    @BeforeEach
    void setUp() {
        controller = new MediaParticipationController(mediaParticipationService);
    }

    @Test
    void yearDelegatesToService() {
        when(mediaParticipationService.getByYear(2025)).thenReturn(List.of());

        ResponseEntity<ApiResponse<List<MediaParticipationResponse>>> response = controller.year(2025);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(mediaParticipationService).getByYear(2025);
    }

    @Test
    void outletDelegatesToService() {
        when(mediaParticipationService.getByMediaOutletId("mo-1")).thenReturn(List.of());

        ResponseEntity<ApiResponse<List<MediaParticipationResponse>>> response = controller.outlet("mo-1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(mediaParticipationService).getByMediaOutletId("mo-1");
    }

    @Test
    void filterConvertsBlankOutletToNull() {
        controller.filter(2026, MediaTier.GOLD, "  ");

        verify(mediaParticipationService).filter(2026, MediaTier.GOLD, null);
    }
}
