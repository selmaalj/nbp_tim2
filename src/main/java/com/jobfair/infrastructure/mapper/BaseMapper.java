package com.jobfair.infrastructure.mapper;

import org.mapstruct.MappingTarget;

/**
 * Generic mapper abstraction used by abstract service layer.
 *
 * @param <T>          entity type
 * @param <RequestDTO> request DTO
 * @param <ResponseDTO> response DTO
 */
public interface BaseMapper<T, RequestDTO, ResponseDTO> {

    T toEntity(RequestDTO request);

    ResponseDTO toResponse(T entity);

    void updateEntity(@MappingTarget T entity, RequestDTO request);

    void patchEntity(@MappingTarget T entity, RequestDTO request);
}
