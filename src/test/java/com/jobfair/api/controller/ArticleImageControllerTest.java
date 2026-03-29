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

import com.jobfair.api.dto.response.ArticleImageResponse;
import com.jobfair.domain.service.ArticleImageService;
import com.jobfair.shared.response.ApiResponse;

@ExtendWith(MockitoExtension.class)
class ArticleImageControllerTest {

    @Mock
    private ArticleImageService articleImageService;

    private ArticleImageController controller;

    @BeforeEach
    void setUp() {
        controller = new ArticleImageController(articleImageService);
    }

    @Test
    void articleDelegatesToService() {
        when(articleImageService.getByArticleId("a1")).thenReturn(List.of());

        ResponseEntity<ApiResponse<List<ArticleImageResponse>>> response = controller.article("a1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(articleImageService).getByArticleId("a1");
    }

    @Test
    void mediaDelegatesToService() {
        when(articleImageService.getByMediaId("m1")).thenReturn(List.of());

        ResponseEntity<ApiResponse<List<ArticleImageResponse>>> response = controller.media("m1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(articleImageService).getByMediaId("m1");
    }

    @Test
    void filterConvertsBlankStringsToNull() {
        controller.filter("  ", " ", "   ", 1, 5);

        verify(articleImageService).filter(null, null, null, 1, 5);
    }
}
