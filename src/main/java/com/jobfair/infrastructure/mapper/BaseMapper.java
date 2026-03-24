package com.jobfair.infrastructure.mapper;

/**
 * Generic mapper abstraction used by abstract service layer.
 *
 * @param <T> entity type
 * @param <RequestDTO> request DTO
 * @param <ResponseDTO> response DTO
 */
public interface BaseMapper<T, RequestDTO, ResponseDTO> {

    T toEntity(RequestDTO request);

    ResponseDTO toResponse(T entity);

    void updateEntity(T entity, RequestDTO request);

    void patchEntity(T entity, RequestDTO request);
}
