package com.jobfair.infrastructure.mapper;

import com.jobfair.api.dto.request.StatBoardRequest;
import com.jobfair.api.dto.response.StatBoardResponse;
import com.jobfair.domain.model.StatBoard;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface StatBoardMapper extends BaseMapper<StatBoard, StatBoardRequest, StatBoardResponse> {

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "stats", ignore = true)
    StatBoard toEntity(StatBoardRequest request);

    @Override
    StatBoardResponse toResponse(StatBoard entity);

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "stats", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntity(@MappingTarget StatBoard entity, StatBoardRequest request);

    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "stats", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void patchEntity(@MappingTarget StatBoard entity, StatBoardRequest request);
}
