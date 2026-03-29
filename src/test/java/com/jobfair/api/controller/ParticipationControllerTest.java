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

import com.jobfair.api.dto.response.ParticipationResponse;
import com.jobfair.domain.service.ParticipationService;
import com.jobfair.shared.response.ApiResponse;

@ExtendWith(MockitoExtension.class)
class ParticipationControllerTest {

    @Mock
    private ParticipationService participationService;

    private ParticipationController controller;

    @BeforeEach
    void setUp() {
        controller = new ParticipationController(participationService);
    }

    @Test
    void yearDelegatesToService() {
        when(participationService.getByYear(2025)).thenReturn(List.of());

        ResponseEntity<ApiResponse<List<ParticipationResponse>>> response = controller.year(2025);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(participationService).getByYear(2025);
    }

    @Test
    void organizationDelegatesToService() {
        when(participationService.getByOrganizationId("org-1")).thenReturn(List.of());

        ResponseEntity<ApiResponse<List<ParticipationResponse>>> response = controller.organization("org-1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(participationService).getByOrganizationId("org-1");
    }

    @Test
    void filterConvertsBlankOrganizationAndTierToNull() {
        controller.filter(2026, "  ", "   ");

        verify(participationService).filter(2026, null, null);
    }
}
