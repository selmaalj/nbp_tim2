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

import com.jobfair.api.dto.response.PackageTierResponse;
import com.jobfair.domain.service.PackageTierService;
import com.jobfair.shared.response.ApiResponse;

@ExtendWith(MockitoExtension.class)
class PackageTierControllerTest {

    @Mock
    private PackageTierService packageTierService;

    private PackageTierController controller;

    @BeforeEach
    void setUp() {
        controller = new PackageTierController(packageTierService);
    }

    @Test
    void codeReturnsOkResponse() {
        when(packageTierService.getByCode("gold")).thenReturn(null);

        ResponseEntity<ApiResponse<PackageTierResponse>> response = controller.code("gold");
        ApiResponse<PackageTierResponse> body = response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(body);
        assertEquals("Package tier by code", body.message());
        verify(packageTierService).getByCode("gold");
    }

    @Test
    void searchTrimsInput() {
        controller.search("  premium  ");

        verify(packageTierService).search("premium");
    }

    @Test
    void filterConvertsBlankStringsToNull() {
        controller.filter("   ", "   ");

        verify(packageTierService).filter(null, null);
    }

    @Test
    void filterDelegatesValues() {
        controller.filter("label", "code");

        verify(packageTierService).filter("label", "code");
    }
}
