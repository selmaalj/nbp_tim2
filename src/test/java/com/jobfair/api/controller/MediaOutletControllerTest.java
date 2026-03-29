package com.jobfair.api.controller;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.http.ResponseEntity;

import com.jobfair.api.dto.response.MediaOutletResponse;
import com.jobfair.domain.model.enums.MediaKind;
import com.jobfair.domain.service.MediaOutletService;
import com.jobfair.shared.response.ApiResponse;

class MediaOutletControllerTest {

    private final MediaOutletService mediaOutletService = Mockito.mock(MediaOutletService.class);
    private final MediaOutletController controller = new MediaOutletController(mediaOutletService);

    @Test
    void slugDelegatesAndReturnsPayload() {
        MediaOutletResponse dto = new MediaOutletResponse("1", "Outlet", "outlet", null, MediaKind.ONLINE, null);
        when(mediaOutletService.getBySlug("outlet")).thenReturn(dto);

        ResponseEntity<ApiResponse<MediaOutletResponse>> response = controller.slug("outlet");
        ApiResponse<MediaOutletResponse> body = response.getBody();

        assertNotNull(body);
        assertEquals(dto, body.data());
    }

    @Test
    void kindDelegatesAndReturnsPayload() {
        MediaOutletResponse dto = new MediaOutletResponse("1", "Outlet", "outlet", null, MediaKind.RADIO, null);
        when(mediaOutletService.getByKind(MediaKind.RADIO)).thenReturn(List.of(dto));

        ResponseEntity<ApiResponse<List<MediaOutletResponse>>> response = controller.kind(MediaKind.RADIO);
        ApiResponse<List<MediaOutletResponse>> body = response.getBody();

        assertNotNull(body);
        assertNotNull(body.data());
        assertEquals(1, body.data().size());
    }

    @Test
    void searchTrimsInput() {
        controller.search("  media  ");

        verify(mediaOutletService).search("media");
    }

    @Test
    void filterConvertsBlankQueryToNull() {
        controller.filter("   ", MediaKind.PRINT, false);

        verify(mediaOutletService).filter(null, MediaKind.PRINT, false);
    }
}
