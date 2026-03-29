package com.jobfair.infrastructure.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.jobfair.api.dto.request.OrganizationRequest;
import com.jobfair.api.dto.response.OrganizationResponse;
import com.jobfair.domain.model.Organization;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrganizationMapper extends BaseMapper<Organization, OrganizationRequest, OrganizationResponse> {

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "logo", ignore = true)
    @Mapping(target = "participations", ignore = true)
    @Mapping(target = "jobs", ignore = true)
    @Mapping(target = "galleryImages", ignore = true)
    Organization toEntity(OrganizationRequest request);

    @Override
    OrganizationResponse toResponse(Organization entity);

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "logo", ignore = true)
    @Mapping(target = "participations", ignore = true)
    @Mapping(target = "jobs", ignore = true)
    @Mapping(target = "galleryImages", ignore = true)
    void updateEntity(@MappingTarget Organization entity, OrganizationRequest request);

    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "logo", ignore = true)
    @Mapping(target = "participations", ignore = true)
    @Mapping(target = "jobs", ignore = true)
    @Mapping(target = "galleryImages", ignore = true)
    void patchEntity(@MappingTarget Organization entity, OrganizationRequest request);
}
