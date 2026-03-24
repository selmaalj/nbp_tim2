package com.jobfair.infrastructure.mapper;

import com.jobfair.api.dto.request.MediaOutletRequest;
import com.jobfair.api.dto.response.MediaOutletResponse;
import com.jobfair.domain.model.MediaOutlet;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface MediaOutletMapper extends BaseMapper<MediaOutlet, MediaOutletRequest, MediaOutletResponse> {

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "logo", ignore = true)
    @Mapping(target = "participations", ignore = true)
    MediaOutlet toEntity(MediaOutletRequest request);

    @Override
    MediaOutletResponse toResponse(MediaOutlet entity);

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "logo", ignore = true)
    @Mapping(target = "participations", ignore = true)
    void updateEntity(@MappingTarget MediaOutlet entity, MediaOutletRequest request);

    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "logo", ignore = true)
    @Mapping(target = "participations", ignore = true)
    void patchEntity(@MappingTarget MediaOutlet entity, MediaOutletRequest request);
}
