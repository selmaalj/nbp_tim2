package com.jobfair.infrastructure.mapper;

import com.jobfair.api.dto.request.MediaParticipationRequest;
import com.jobfair.api.dto.response.MediaParticipationResponse;
import com.jobfair.domain.model.MediaParticipation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface MediaParticipationMapper extends BaseMapper<MediaParticipation, MediaParticipationRequest, MediaParticipationResponse> {

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "outlet", ignore = true)
    MediaParticipation toEntity(MediaParticipationRequest request);

    @Override
    @Mapping(target = "mediaOutletId", source = "outlet.id")
    MediaParticipationResponse toResponse(MediaParticipation entity);

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "outlet", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntity(@MappingTarget MediaParticipation entity, MediaParticipationRequest request);

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "outlet", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void patchEntity(@MappingTarget MediaParticipation entity, MediaParticipationRequest request);
}
