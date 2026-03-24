package com.jobfair.api.controller;

import com.jobfair.domain.service.BaseCrudService;
import com.jobfair.shared.response.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Generic REST controller that centralizes standard CRUD endpoints
 * and response wrapping for all API resources.
 */
public abstract class AbstractCrudController<ID, RequestDTO, ResponseDTO> {

    private final BaseCrudService<?, ID, RequestDTO, ResponseDTO> service;

    protected AbstractCrudController(BaseCrudService<?, ID, RequestDTO, ResponseDTO> service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ResponseDTO>> create(@Valid @RequestBody RequestDTO request) {
        ResponseDTO created = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(resourceName() + " created successfully", created));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ResponseDTO>>> getAll() {
        List<ResponseDTO> payload = service.getAll();
        return ResponseEntity.ok(ApiResponse.success(resourceNamePlural() + " fetched successfully", payload));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ResponseDTO>> getById(@PathVariable ID id) {
        ResponseDTO payload = service.getById(id);
        return ResponseEntity.ok(ApiResponse.success(resourceName() + " fetched successfully", payload));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ResponseDTO>> update(@PathVariable ID id, @Valid @RequestBody RequestDTO request) {
        ResponseDTO payload = service.update(id, request);
        return ResponseEntity.ok(ApiResponse.success(resourceName() + " updated successfully", payload));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<ResponseDTO>> patch(@PathVariable ID id, @Valid @RequestBody RequestDTO request) {
        ResponseDTO payload = service.patch(id, request);
        return ResponseEntity.ok(ApiResponse.success(resourceName() + " patched successfully", payload));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable ID id) {
        service.delete(id);
        return ResponseEntity.ok(ApiResponse.success(resourceName() + " deleted successfully", null));
    }

    protected abstract String resourceName();

    protected abstract String resourceNamePlural();
}
