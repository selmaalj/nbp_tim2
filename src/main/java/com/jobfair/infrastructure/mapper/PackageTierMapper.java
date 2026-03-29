package com.jobfair.infrastructure.mapper;

import com.jobfair.api.dto.request.PackageTierRequest;
import com.jobfair.api.dto.response.PackageTierResponse;
import com.jobfair.domain.model.PackageTier;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PackageTierMapper extends BaseMapper<PackageTier, PackageTierRequest, PackageTierResponse> {

    @Override
    @Mapping(target = "id", ignore = true)
    PackageTier toEntity(PackageTierRequest request);

    @Override
    PackageTierResponse toResponse(PackageTier entity);

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntity(@MappingTarget PackageTier entity, PackageTierRequest request);

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void patchEntity(@MappingTarget PackageTier entity, PackageTierRequest request);
}
