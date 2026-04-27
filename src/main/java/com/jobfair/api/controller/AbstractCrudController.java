package com.jobfair.api.controller;

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

import com.jobfair.domain.service.BaseCrudService;
import com.jobfair.shared.docs.EndpointDocType;
import com.jobfair.shared.docs.EndpointDocumentation;
import com.jobfair.shared.docs.ErrorDocProfile;
import com.jobfair.shared.response.ApiResponse;

import jakarta.validation.Valid;

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
    @EndpointDocumentation(type = EndpointDocType.CREATE, order = 10, expectedStatus = HttpStatus.CREATED, errorProfiles = ErrorDocProfile.JSON_BODY)
    public ResponseEntity<ApiResponse<ResponseDTO>> create(@Valid @RequestBody RequestDTO request) {
        ResponseDTO created = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(resourceName() + " created successfully", created));
    }

    @GetMapping
    @EndpointDocumentation(type = EndpointDocType.GET_ALL, order = 20)
    public ResponseEntity<ApiResponse<List<ResponseDTO>>> getAll() {
        List<ResponseDTO> payload = service.getAll();
        return ResponseEntity.ok(ApiResponse.success(resourceNamePlural() + " fetched successfully", payload));
    }

    @GetMapping("/{id}")
    @EndpointDocumentation(type = EndpointDocType.GET_BY_ID, order = 30, errorProfiles = ErrorDocProfile.NOT_FOUND)
    public ResponseEntity<ApiResponse<ResponseDTO>> getById(@PathVariable ID id) {
        ResponseDTO payload = service.getById(id);
        return ResponseEntity.ok(ApiResponse.success(resourceName() + " fetched successfully", payload));
    }

    @PutMapping("/{id}")
    @EndpointDocumentation(type = EndpointDocType.UPDATE, order = 40, errorProfiles = ErrorDocProfile.JSON_BODY_AND_NOT_FOUND)
    public ResponseEntity<ApiResponse<ResponseDTO>> update(@PathVariable ID id, @Valid @RequestBody RequestDTO request) {
        ResponseDTO payload = service.update(id, request);
        return ResponseEntity.ok(ApiResponse.success(resourceName() + " updated successfully", payload));
    }

    @PatchMapping("/{id}")
    @EndpointDocumentation(type = EndpointDocType.PATCH, order = 50, errorProfiles = ErrorDocProfile.JSON_BODY_AND_NOT_FOUND)
    public ResponseEntity<ApiResponse<ResponseDTO>> patch(@PathVariable ID id, @Valid @RequestBody RequestDTO request) {
        ResponseDTO payload = service.patch(id, request);
        return ResponseEntity.ok(ApiResponse.success(resourceName() + " patched successfully", payload));
    }

    @DeleteMapping("/{id}")
    @EndpointDocumentation(type = EndpointDocType.DELETE, order = 60, errorProfiles = ErrorDocProfile.NOT_FOUND)
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable ID id) {
        service.delete(id);
        return ResponseEntity.ok(ApiResponse.success(resourceName() + " deleted successfully", null));
    }

    protected abstract String resourceName();

    protected abstract String resourceNamePlural();
}
