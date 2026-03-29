package com.jobfair.infrastructure.mapper;

import com.jobfair.api.dto.request.CommitteeRequest;
import com.jobfair.api.dto.response.CommitteeResponse;
import com.jobfair.domain.model.Committee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CommitteeMapper extends BaseMapper<Committee, CommitteeRequest, CommitteeResponse> {

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "members", ignore = true)
    Committee toEntity(CommitteeRequest request);

    @Override
    CommitteeResponse toResponse(Committee entity);

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "members", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntity(@MappingTarget Committee entity, CommitteeRequest request);

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "members", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void patchEntity(@MappingTarget Committee entity, CommitteeRequest request);
}
