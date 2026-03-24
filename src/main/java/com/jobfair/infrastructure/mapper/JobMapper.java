package com.jobfair.infrastructure.mapper;

import com.jobfair.api.dto.request.JobRequest;
import com.jobfair.api.dto.response.JobResponse;
import com.jobfair.domain.model.Job;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface JobMapper extends BaseMapper<Job, JobRequest, JobResponse> {

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "organization", ignore = true)
    @Mapping(target = "tags", ignore = true)
    Job toEntity(JobRequest request);

    @Override
    JobResponse toResponse(Job entity);

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "organization", ignore = true)
    @Mapping(target = "tags", ignore = true)
    void updateEntity(@MappingTarget Job entity, JobRequest request);

    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "organization", ignore = true)
    @Mapping(target = "tags", ignore = true)
    void patchEntity(@MappingTarget Job entity, JobRequest request);
}
