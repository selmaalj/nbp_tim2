package com.jobfair.application.serviceImpl;

import com.jobfair.domain.repository.GenericRepository;
import com.jobfair.domain.service.BaseCrudService;
import com.jobfair.infrastructure.mapper.BaseMapper;
import com.jobfair.shared.exception.ResourceNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

/**
 * Reusable CRUD implementation that centralizes read/write workflows,
 * DTO mapping and existence checks for all aggregate services.
 */
@RequiredArgsConstructor
@Transactional
public abstract class AbstractCrudService<T, ID, RequestDTO, ResponseDTO>
        implements BaseCrudService<T, ID, RequestDTO, ResponseDTO> {

    private final GenericRepository<T, ID> repository;
    private final BaseMapper<T, RequestDTO, ResponseDTO> mapper;

    @Override
    public ResponseDTO create(RequestDTO request) {
        validateForCreate(request);
        T entity = mapper.toEntity(request);
        T saved = repository.save(entity);
        return mapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResponseDTO> getAll() {
        return repository.findAll().stream().map(mapper::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseDTO getById(ID id) {
        return mapper.toResponse(findOrThrow(id));
    }

    @Override
    public ResponseDTO update(ID id, RequestDTO request) {
        validateForUpdate(request);
        T existing = findOrThrow(id);
        mapper.updateEntity(existing, request);
        T saved = repository.save(existing);
        return mapper.toResponse(saved);
    }

    @Override
    public ResponseDTO patch(ID id, RequestDTO request) {
        T existing = findOrThrow(id);
        mapper.patchEntity(existing, request);
        validateAfterPatch(existing);
        T saved = repository.save(existing);
        return mapper.toResponse(saved);
    }

    @Override
    public void delete(ID id) {
        repository.delete(findOrThrow(id));
    }

    protected void validateForCreate(RequestDTO request) {
    }

    protected void validateForUpdate(RequestDTO request) {
    }

    protected void validateAfterPatch(T entity) {
    }

    protected abstract String resourceName();

    protected T findOrThrow(ID id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(resourceName() + " not found with id: " + id));
    }
}
