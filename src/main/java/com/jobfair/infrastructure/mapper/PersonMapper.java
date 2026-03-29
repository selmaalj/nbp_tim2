package com.jobfair.infrastructure.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.jobfair.api.dto.request.PersonRequest;
import com.jobfair.api.dto.response.PersonResponse;
import com.jobfair.domain.model.Person;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PersonMapper extends BaseMapper<Person, PersonRequest, PersonResponse> {

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "image", ignore = true)
    @Mapping(target = "committees", ignore = true)
    Person toEntity(PersonRequest request);

    @Override
    PersonResponse toResponse(Person entity);

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "image", ignore = true)
    @Mapping(target = "committees", ignore = true)
    void updateEntity(@MappingTarget Person entity, PersonRequest request);

    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "image", ignore = true)
    @Mapping(target = "committees", ignore = true)
    void patchEntity(@MappingTarget Person entity, PersonRequest request);
}
