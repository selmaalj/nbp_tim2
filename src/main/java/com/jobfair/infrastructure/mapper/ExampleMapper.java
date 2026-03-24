package com.jobfair.infrastructure.mapper;

import com.jobfair.api.dto.request.ExampleRequest;
import com.jobfair.api.dto.response.ExampleResponse;
import com.jobfair.domain.model.ExampleEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface ExampleMapper extends BaseMapper<ExampleEntity, ExampleRequest, ExampleResponse> {

    @Override
    @Mapping(target = "id", ignore = true)
    ExampleEntity toEntity(ExampleRequest request);

    @Override
    @Mapping(target = "createdAt", source = "createdAt")
    ExampleResponse toResponse(ExampleEntity entity);

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntity(@MappingTarget ExampleEntity entity, ExampleRequest request);

    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void patchEntity(@MappingTarget ExampleEntity entity, ExampleRequest request);
}
