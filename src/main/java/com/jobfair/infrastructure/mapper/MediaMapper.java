package com.jobfair.infrastructure.mapper;

import com.jobfair.api.dto.request.MediaRequest;
import com.jobfair.api.dto.response.MediaResponse;
import com.jobfair.domain.model.Media;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface MediaMapper extends BaseMapper<Media, MediaRequest, MediaResponse> {

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "width", ignore = true)
    @Mapping(target = "height", ignore = true)
    @Mapping(target = "sizeBytes", ignore = true)
    @Mapping(target = "mimeType", ignore = true)
    Media toEntity(MediaRequest request);

    @Override
    MediaResponse toResponse(Media entity);

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "width", ignore = true)
    @Mapping(target = "height", ignore = true)
    @Mapping(target = "sizeBytes", ignore = true)
    @Mapping(target = "mimeType", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntity(@MappingTarget Media entity, MediaRequest request);

    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "width", ignore = true)
    @Mapping(target = "height", ignore = true)
    @Mapping(target = "sizeBytes", ignore = true)
    @Mapping(target = "mimeType", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void patchEntity(@MappingTarget Media entity, MediaRequest request);
}
