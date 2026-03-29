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
import org.springframework.http.ResponseEntity;

import com.jobfair.api.dto.response.OrganizationResponse;
import com.jobfair.domain.model.enums.OrganizationType;
import com.jobfair.domain.service.OrganizationService;
import com.jobfair.shared.response.ApiResponse;

@ExtendWith(MockitoExtension.class)
class OrganizationControllerTest {

    @Mock
    private OrganizationService organizationService;

    private OrganizationController controller;

    @BeforeEach
    @SuppressWarnings("unused")
    void setUp() {
        controller = new OrganizationController(organizationService);
    }

    @Test
    void slugDelegatesAndReturnsPayload() {
        OrganizationResponse dto = new OrganizationResponse("1", OrganizationType.COMPANY, "Acme", "acme", null, null, null);
        when(organizationService.getBySlug("acme")).thenReturn(dto);

        ResponseEntity<ApiResponse<OrganizationResponse>> response = controller.slug("acme");
        ApiResponse<OrganizationResponse> body = response.getBody();

        assertNotNull(body);
        assertEquals(dto, body.data());
        assertEquals("Organization by slug", body.message());
    }

    @Test
    void typeDelegatesAndReturnsPayload() {
        OrganizationResponse dto = new OrganizationResponse("1", OrganizationType.NGO, "NGO", "ngo", null, null, null);
        when(organizationService.getByType(OrganizationType.NGO)).thenReturn(List.of(dto));

        ResponseEntity<ApiResponse<List<OrganizationResponse>>> response = controller.type(OrganizationType.NGO);
        ApiResponse<List<OrganizationResponse>> body = response.getBody();

        assertNotNull(body);
        assertNotNull(body.data());
        assertEquals(1, body.data().size());
    }

    @Test
    void searchTrimsInput() {
        controller.search("  org  ");

        verify(organizationService).search("org");
    }

    @Test
    void filterConvertsBlankQueryToNull() {
        controller.filter("   ", OrganizationType.INSTITUTION, true);

        verify(organizationService).filter(null, OrganizationType.INSTITUTION, true);
    }
}
