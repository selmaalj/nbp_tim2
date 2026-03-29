package com.jobfair.infrastructure.mapper;

import com.jobfair.api.dto.request.ParticipationRequest;
import com.jobfair.api.dto.response.ParticipationResponse;
import com.jobfair.domain.model.Participation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ParticipationMapper extends BaseMapper<Participation, ParticipationRequest, ParticipationResponse> {

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "organization", ignore = true)
    @Mapping(target = "packageTier", ignore = true)
    Participation toEntity(ParticipationRequest request);

    @Override
    @Mapping(target = "organizationId", source = "organization.id")
    @Mapping(target = "packageTierId", source = "packageTier.id")
    ParticipationResponse toResponse(Participation entity);

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "organization", ignore = true)
    @Mapping(target = "packageTier", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntity(@MappingTarget Participation entity, ParticipationRequest request);

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "organization", ignore = true)
    @Mapping(target = "packageTier", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void patchEntity(@MappingTarget Participation entity, ParticipationRequest request);
}
