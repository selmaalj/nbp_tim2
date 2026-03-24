package com.jobfair.application.serviceImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.jobfair.api.dto.request.ExampleRequest;
import com.jobfair.api.dto.response.ExampleResponse;
import com.jobfair.domain.model.ExampleEntity;
import com.jobfair.domain.repository.ExampleRepository;
import com.jobfair.infrastructure.mapper.ExampleMapper;
import com.jobfair.shared.exception.ResourceNotFoundException;

@ExtendWith(MockitoExtension.class)
class ExampleServiceImplTest {

    @Mock
    private ExampleRepository repository;

    @Mock
    private ExampleMapper mapper;

    @InjectMocks
    private ExampleServiceImpl service;

    @Test
    void getById_ShouldReturnResponse_WhenEntityExists() {
        Long id = 1L;
        ExampleEntity entity = ExampleEntity.builder()
                .id(id)
                .title("Title")
                .description("Description")
                .build();
        entity.setCreatedAt(LocalDateTime.now());

        ExampleResponse response = new ExampleResponse(id, "Title", "Description", entity.getCreatedAt());

        when(repository.findById(id)).thenReturn(Optional.of(entity));
        when(mapper.toResponse(entity)).thenReturn(response);

        ExampleResponse result = service.getById(id);

        assertEquals(id, result.id());
        verify(repository).findById(id);
    }

    @Test
    void getById_ShouldThrow_WhenEntityMissing() {
        Long id = 99L;
        when(repository.findById(id)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> service.getById(id));

        assertEquals("ExampleEntity not found with id: " + id, exception.getMessage());
    }

    @Test
    void getAll_ShouldReturnMappedList() {
        ExampleEntity first = ExampleEntity.builder().id(1L).title("A").description("AA").build();
        ExampleEntity second = ExampleEntity.builder().id(2L).title("B").description("BB").build();

        ExampleResponse firstResponse = new ExampleResponse(1L, "A", "AA", null);
        ExampleResponse secondResponse = new ExampleResponse(2L, "B", "BB", null);

        when(repository.findAll()).thenReturn(List.of(first, second));
        when(mapper.toResponse(first)).thenReturn(firstResponse);
        when(mapper.toResponse(second)).thenReturn(secondResponse);

        List<ExampleResponse> result = service.getAll();

        assertEquals(2, result.size());
        assertEquals("A", result.get(0).title());
        assertEquals("B", result.get(1).title());
    }

    @Test
    void create_ShouldPersist_WhenRequestValid() {
        ExampleRequest request = new ExampleRequest("Title", "Description");
        ExampleEntity toSave = ExampleEntity.builder().title("Title").description("Description").build();
        ExampleEntity saved = ExampleEntity.builder().id(10L).title("Title").description("Description").build();
        ExampleResponse response = new ExampleResponse(10L, "Title", "Description", null);

        when(mapper.toEntity(request)).thenReturn(toSave);
        when(repository.save(any(ExampleEntity.class))).thenReturn(saved);
        when(mapper.toResponse(saved)).thenReturn(response);

        ExampleResponse result = service.create(request);

        assertEquals(10L, result.id());
        verify(repository).save(toSave);
    }

    @Test
    void create_ShouldThrow_WhenTitleMissing() {
        ExampleRequest request = new ExampleRequest(" ", "Description");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> service.create(request));

        assertEquals("Title is required", exception.getMessage());
        verify(repository, never()).save(any());
    }

    @Test
    void create_ShouldThrow_WhenDescriptionMissing() {
        ExampleRequest request = new ExampleRequest("Title", " ");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> service.create(request));

        assertEquals("Description is required when creating a new example", exception.getMessage());
        verify(repository, never()).save(any());
    }

    @Test
    void update_ShouldPersist_WhenRequestValid() {
        Long id = 7L;
        ExampleRequest request = new ExampleRequest("Updated", "Updated description");
        ExampleEntity existing = ExampleEntity.builder().id(id).title("Old").description("Old desc").build();
        ExampleEntity saved = ExampleEntity.builder().id(id).title("Updated").description("Updated description").build();
        ExampleResponse response = new ExampleResponse(id, "Updated", "Updated description", null);

        when(repository.findById(id)).thenReturn(Optional.of(existing));
        when(repository.save(existing)).thenReturn(saved);
        when(mapper.toResponse(saved)).thenReturn(response);

        ExampleResponse result = service.update(id, request);

        assertEquals("Updated", result.title());
        verify(mapper).updateEntity(existing, request);
        verify(repository).save(existing);
    }

    @Test
    void update_ShouldThrow_WhenTitleMissing() {
        ExampleRequest request = new ExampleRequest("", "Description");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> service.update(1L, request));

        assertEquals("Title is required", exception.getMessage());
        verify(repository, never()).findById(any());
    }

    @Test
    void update_ShouldThrow_WhenEntityMissing() {
        Long id = 123L;
        ExampleRequest request = new ExampleRequest("Title", "Description");
        when(repository.findById(id)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> service.update(id, request));

        assertEquals("ExampleEntity not found with id: " + id, exception.getMessage());
    }

    @Test
    void patch_ShouldPersist_WhenPatchedTitleValid() {
        Long id = 9L;
        ExampleRequest request = new ExampleRequest("Patched", null);
        ExampleEntity existing = ExampleEntity.builder().id(id).title("Old").description("Desc").build();
        ExampleEntity saved = ExampleEntity.builder().id(id).title("Patched").description("Desc").build();
        ExampleResponse response = new ExampleResponse(id, "Patched", "Desc", null);

        when(repository.findById(id)).thenReturn(Optional.of(existing));
        when(repository.save(existing)).thenReturn(saved);
        when(mapper.toResponse(saved)).thenReturn(response);

        ExampleResponse result = service.patch(id, request);

        assertEquals("Patched", result.title());
        verify(mapper).patchEntity(existing, request);
        verify(repository).save(existing);
    }

    @Test
    void patch_ShouldThrow_WhenPatchedTitleBlank() {
        Long id = 11L;
        ExampleRequest request = new ExampleRequest("", null);
        ExampleEntity existing = ExampleEntity.builder().id(id).title("").description("Desc").build();

        when(repository.findById(id)).thenReturn(Optional.of(existing));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> service.patch(id, request));

        assertEquals("Title is required", exception.getMessage());
    }

    @Test
    void patch_ShouldThrow_WhenEntityMissing() {
        Long id = 124L;
        when(repository.findById(id)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> service.patch(id, new ExampleRequest("x", null)));

        assertEquals("ExampleEntity not found with id: " + id, exception.getMessage());
    }

    @Test
    void delete_ShouldRemove_WhenEntityExists() {
        Long id = 15L;
        ExampleEntity existing = ExampleEntity.builder().id(id).title("T").description("D").build();
        when(repository.findById(id)).thenReturn(Optional.of(existing));

        service.delete(id);

        verify(repository).delete(existing);
    }

    @Test
    void delete_ShouldThrow_WhenEntityMissing() {
        Long id = 125L;
        when(repository.findById(id)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> service.delete(id));

        assertEquals("ExampleEntity not found with id: " + id, exception.getMessage());
    }
}
