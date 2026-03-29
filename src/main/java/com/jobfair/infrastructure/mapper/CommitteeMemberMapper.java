package com.jobfair.infrastructure.mapper;

import com.jobfair.api.dto.request.CommitteeMemberRequest;
import com.jobfair.api.dto.response.CommitteeMemberResponse;
import com.jobfair.domain.model.CommitteeMember;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CommitteeMemberMapper extends BaseMapper<CommitteeMember, CommitteeMemberRequest, CommitteeMemberResponse> {

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "committee", ignore = true)
    @Mapping(target = "person", ignore = true)
    CommitteeMember toEntity(CommitteeMemberRequest request);

    @Override
    @Mapping(target = "committeeId", source = "committee.id")
    @Mapping(target = "personId", source = "person.id")
    CommitteeMemberResponse toResponse(CommitteeMember entity);

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "committee", ignore = true)
    @Mapping(target = "person", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntity(@MappingTarget CommitteeMember entity, CommitteeMemberRequest request);

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "committee", ignore = true)
    @Mapping(target = "person", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void patchEntity(@MappingTarget CommitteeMember entity, CommitteeMemberRequest request);
}
