package com.jobfair.domain.service;

import java.util.List;

/**
 * Generic CRUD contract reusable across all aggregates.
 *
 * @param <T> entity type
 * @param <ID> identifier type
 * @param <RequestDTO> request DTO used for create/update/patch
 * @param <ResponseDTO> response DTO returned by read operations
 */
public interface BaseCrudService<T, ID, RequestDTO, ResponseDTO> {

    ResponseDTO create(RequestDTO request);

    List<ResponseDTO> getAll();

    ResponseDTO getById(ID id);

    ResponseDTO update(ID id, RequestDTO request);

    ResponseDTO patch(ID id, RequestDTO request);

    void delete(ID id);
}
