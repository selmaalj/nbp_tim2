package com.jobfair.api.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobfair.api.dto.request.ExampleRequest;
import com.jobfair.api.dto.response.ExampleResponse;
import com.jobfair.domain.service.ExampleService;
import com.jobfair.shared.exception.GlobalExceptionHandler;
import com.jobfair.shared.exception.ResourceNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = ExampleController.class)
@Import(GlobalExceptionHandler.class)
class ExampleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ExampleService exampleService;

    @Test
    void create_ShouldReturnCreated_WhenPayloadValid() throws Exception {
        ExampleRequest request = new ExampleRequest("Title", "Description");
        ExampleResponse response = new ExampleResponse(1L, "Title", "Description", LocalDateTime.now());
        when(exampleService.create(any(ExampleRequest.class))).thenReturn(response);

        mockMvc.perform(post("/examples")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").value(1L))
                .andExpect(jsonPath("$.data.title").value("Title"));
    }

    @Test
    void create_ShouldReturnBadRequest_WhenValidationFails() throws Exception {
        ExampleRequest request = new ExampleRequest("x".repeat(151), "Description");

        mockMvc.perform(post("/examples")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Validation failed"));
    }

    @Test
    void create_ShouldReturnBadRequest_WhenServiceRejectsPayload() throws Exception {
        ExampleRequest request = new ExampleRequest("", "Description");
        when(exampleService.create(any(ExampleRequest.class))).thenThrow(new IllegalArgumentException("Title is required"));

        mockMvc.perform(post("/examples")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Title is required"));
    }

    @Test
    void create_ShouldReturnBadRequest_WhenJsonMalformed() throws Exception {
        mockMvc.perform(post("/examples")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{bad-json}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Malformed JSON request body"));
    }

    @Test
    void getAll_ShouldReturnList() throws Exception {
        ExampleResponse first = new ExampleResponse(1L, "A", "AA", LocalDateTime.now());
        ExampleResponse second = new ExampleResponse(2L, "B", "BB", LocalDateTime.now());
        when(exampleService.getAll()).thenReturn(List.of(first, second));

        mockMvc.perform(get("/examples"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data", hasSize(2)));
    }

    @Test
    void getById_ShouldReturnItem_WhenExists() throws Exception {
        ExampleResponse response = new ExampleResponse(5L, "Title", "Description", LocalDateTime.now());
        when(exampleService.getById(5L)).thenReturn(response);

        mockMvc.perform(get("/examples/{id}", 5L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(5L));
    }

    @Test
    void getById_ShouldReturnNotFound_WhenMissing() throws Exception {
        when(exampleService.getById(77L)).thenThrow(new ResourceNotFoundException("ExampleEntity not found with id: 77"));

        mockMvc.perform(get("/examples/{id}", 77L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("ExampleEntity not found with id: 77"));
    }

    @Test
    void update_ShouldReturnUpdatedEntity() throws Exception {
        ExampleRequest request = new ExampleRequest("Updated", "Updated description");
        ExampleResponse response = new ExampleResponse(3L, "Updated", "Updated description", LocalDateTime.now());
        when(exampleService.update(eq(3L), any(ExampleRequest.class))).thenReturn(response);

        mockMvc.perform(put("/examples/{id}", 3L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.title").value("Updated"));
    }

    @Test
    void patch_ShouldReturnPatchedEntity() throws Exception {
        ExampleRequest request = new ExampleRequest("Patched", null);
        ExampleResponse response = new ExampleResponse(4L, "Patched", "Description", LocalDateTime.now());
        when(exampleService.patch(eq(4L), any(ExampleRequest.class))).thenReturn(response);

        mockMvc.perform(patch("/examples/{id}", 4L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.title").value("Patched"));
    }

    @Test
    void delete_ShouldReturnOk_WhenEntityExists() throws Exception {
        doNothing().when(exampleService).delete(9L);

        mockMvc.perform(delete("/examples/{id}", 9L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    void delete_ShouldReturnNotFound_WhenEntityMissing() throws Exception {
        doThrow(new ResourceNotFoundException("ExampleEntity not found with id: 99")).when(exampleService).delete(99L);

        mockMvc.perform(delete("/examples/{id}", 99L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("ExampleEntity not found with id: 99"));
    }

    @Test
    void oldRoute_ShouldReturnNotFound() throws Exception {
        mockMvc.perform(get("/api/v1/examples"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Route not found: api/v1/examples"));
    }
}
