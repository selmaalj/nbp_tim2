package com.jobfair.infrastructure.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.jobfair.api.dto.request.ArticleRequest;
import com.jobfair.api.dto.response.ArticleResponse;
import com.jobfair.domain.model.Article;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ArticleMapper extends BaseMapper<Article, ArticleRequest, ArticleResponse> {

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "images", ignore = true)
    Article toEntity(ArticleRequest request);

    @Override
    ArticleResponse toResponse(Article entity);

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "images", ignore = true)
    void updateEntity(@MappingTarget Article entity, ArticleRequest request);

    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "images", ignore = true)
    void patchEntity(@MappingTarget Article entity, ArticleRequest request);
}
