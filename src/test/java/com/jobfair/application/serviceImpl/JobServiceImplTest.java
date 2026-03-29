package com.jobfair.application.serviceImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.jobfair.api.dto.response.JobResponse;
import com.jobfair.domain.model.Job;
import com.jobfair.domain.repository.JobRepository;
import com.jobfair.infrastructure.mapper.JobMapper;
import com.jobfair.shared.exception.ResourceNotFoundException;

@ExtendWith(MockitoExtension.class)
class JobServiceImplTest {

    @Mock
    private JobRepository repository;

    @Mock
    private JobMapper mapper;

    private JobServiceImpl service;

    @BeforeEach
    @SuppressWarnings("unused")
    void setUp() {
        service = new JobServiceImpl(repository, mapper);
    }

    @Test
    void getBySlugReturnsMappedResponse() {
        Job job = new Job();
        JobResponse response = new JobResponse("1", "Java Dev", "java-dev", "desc", null, null, null, null, null);

        when(repository.findBySlug("java-dev")).thenReturn(Optional.of(job));
        when(mapper.toResponse(job)).thenReturn(response);

        JobResponse result = service.getBySlug("java-dev");

        assertEquals(response, result);
        verify(repository).findBySlug("java-dev");
    }

    @Test
    void getBySlugThrowsWhenMissing() {
        when(repository.findBySlug("missing")).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> service.getBySlug("missing"));
        assertEquals("Job not found with slug: missing", exception.getMessage());
    }

    @Test
    void getActiveUsesNowAndMapsResults() {
        Job job = new Job();
        JobResponse response = new JobResponse("1", "Java Dev", "java-dev", "desc", null, null, null, null, null);

        when(repository.findActive(any(LocalDateTime.class))).thenReturn(List.of(job));
        when(mapper.toResponse(job)).thenReturn(response);

        List<JobResponse> result = service.getActive();

        assertEquals(List.of(response), result);
        verify(repository).findActive(any(LocalDateTime.class));
    }

    @Test
    void searchUsesRichDefaults() {
        Job job = new Job();
        JobResponse response = new JobResponse("1", "Java Dev", "java-dev", "desc", null, null, null, null, null);

        when(repository.searchRich(eq("java"), isNull(), isNull(), eq(false), any(LocalDateTime.class))).thenReturn(List.of(job));
        when(mapper.toResponse(job)).thenReturn(response);

        List<JobResponse> result = service.search("java");

        assertEquals(List.of(response), result);
        verify(repository).searchRich(eq("java"), isNull(), isNull(), eq(false), any(LocalDateTime.class));
    }

    @Test
    void filterForwardsAllArguments() {
        LocalDateTime postedAfter = LocalDateTime.of(2026, 1, 1, 0, 0);
        LocalDateTime expiresBefore = LocalDateTime.of(2026, 5, 1, 0, 0);

        service.filter("backend", postedAfter, expiresBefore, true);

        verify(repository).searchRich(eq("backend"), eq(postedAfter), eq(expiresBefore), eq(true), any(LocalDateTime.class));
    }
}
