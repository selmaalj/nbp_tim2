package com.jobfair.application.serviceImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.jobfair.api.dto.response.ArticleResponse;
import com.jobfair.domain.model.Article;
import com.jobfair.domain.repository.ArticleRepository;
import com.jobfair.infrastructure.mapper.ArticleMapper;
import com.jobfair.shared.exception.ResourceNotFoundException;

@ExtendWith(MockitoExtension.class)
class ArticleServiceImplTest {

    @Mock
    private ArticleRepository repository;

    @Mock
    private ArticleMapper mapper;

    private ArticleServiceImpl service;

    @BeforeEach
    @SuppressWarnings("unused")
    void setUp() {
        service = new ArticleServiceImpl(repository, mapper);
    }

    @Test
    void getBySlugReturnsMappedResponse() {
        String slug = "career-day";
        Article article = new Article();
        ArticleResponse response = new ArticleResponse("1", "Title", slug, "Body", null, false, true, null, null);

        when(repository.findBySlug(slug)).thenReturn(Optional.of(article));
        when(mapper.toResponse(article)).thenReturn(response);

        ArticleResponse result = service.getBySlug(slug);

        assertEquals(response, result);
        verify(repository).findBySlug(slug);
        verify(mapper).toResponse(article);
    }

    @Test
    void getBySlugThrowsWhenMissing() {
        when(repository.findBySlug("missing")).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> service.getBySlug("missing"));
        assertEquals("Article not found with slug: missing", exception.getMessage());
        verify(repository).findBySlug("missing");
    }

    @Test
    void getPublishedReturnsMappedList() {
        Article first = new Article();
        Article second = new Article();
        ArticleResponse firstResponse = new ArticleResponse("1", "A", "a", "body", null, false, true, null, null);
        ArticleResponse secondResponse = new ArticleResponse("2", "B", "b", "body", null, false, true, null, null);

        when(repository.findPublished()).thenReturn(List.of(first, second));
        when(mapper.toResponse(first)).thenReturn(firstResponse);
        when(mapper.toResponse(second)).thenReturn(secondResponse);

        List<ArticleResponse> result = service.getPublished();

        assertEquals(List.of(firstResponse, secondResponse), result);
        verify(repository).findPublished();
    }

    @Test
    void searchUsesRichQueryWithDefaultFilters() {
        Article article = new Article();
        ArticleResponse response = new ArticleResponse("1", "Title", "slug", "body", null, false, true, null, null);

        when(repository.searchRich("java", null, null, null, null)).thenReturn(List.of(article));
        when(mapper.toResponse(article)).thenReturn(response);

        List<ArticleResponse> result = service.search("java");

        assertEquals(List.of(response), result);
        verify(repository).searchRich(eq("java"), isNull(), isNull(), isNull(), isNull());
    }

    @Test
    void filterForwardsAllArguments() {
        LocalDateTime from = LocalDateTime.of(2026, 1, 1, 0, 0);
        LocalDateTime to = LocalDateTime.of(2026, 12, 31, 23, 59);

        service.filter("dev", true, false, from, to);

        verify(repository).searchRich("dev", true, false, from, to);
    }
}
