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

import com.jobfair.api.dto.response.ArticleResponse;
import com.jobfair.domain.service.ArticleService;
import com.jobfair.shared.response.ApiResponse;

@ExtendWith(MockitoExtension.class)
class ArticleControllerTest {

    @Mock
    private ArticleService articleService;

    private ArticleController controller;

    @BeforeEach
    @SuppressWarnings("unused")
    void setUp() {
        controller = new ArticleController(articleService);
    }

    @Test
    void slugReturnsOkResponse() {
        ArticleResponse dto = new ArticleResponse("1", "Title", "slug", "body", null, false, true, null, null);
        when(articleService.getBySlug("slug")).thenReturn(dto);

        ResponseEntity<ApiResponse<ArticleResponse>> response = controller.slug("slug");
        ApiResponse<ArticleResponse> body = response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(body);
        assertEquals("Article by slug", body.message());
        assertEquals(dto, body.data());
    }

    @Test
    void searchTrimsInput() {
        controller.search("  fair  ");

        verify(articleService).search("fair");
    }

    @Test
    void filterConvertsBlankQueryToNull() {
        LocalDateTime from = LocalDateTime.of(2026, 1, 1, 0, 0);
        LocalDateTime to = LocalDateTime.of(2026, 12, 31, 0, 0);

        controller.filter("   ", true, false, from, to);

        verify(articleService).filter(null, true, false, from, to);
    }

    @Test
    void publishedReturnsPayload() {
        ArticleResponse dto = new ArticleResponse("1", "Title", "slug", "body", null, false, true, null, null);
        when(articleService.getPublished()).thenReturn(List.of(dto));

        ResponseEntity<ApiResponse<List<ArticleResponse>>> response = controller.published();
        ApiResponse<List<ArticleResponse>> body = response.getBody();

        assertNotNull(body);
        assertNotNull(body.data());
        assertEquals(1, body.data().size());
        assertEquals("Published articles", body.message());
    }
}
