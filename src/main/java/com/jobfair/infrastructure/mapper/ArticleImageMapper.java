package com.jobfair.infrastructure.mapper;

import com.jobfair.api.dto.request.ArticleImageRequest;
import com.jobfair.api.dto.response.ArticleImageResponse;
import com.jobfair.domain.model.ArticleImage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ArticleImageMapper extends BaseMapper<ArticleImage, ArticleImageRequest, ArticleImageResponse> {

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "article", ignore = true)
    @Mapping(target = "media", ignore = true)
    @Mapping(target = "caption", source = "altText")
    @Mapping(target = "displayOrder", source = "sort")
    ArticleImage toEntity(ArticleImageRequest request);

    @Override
    @Mapping(target = "articleId", source = "article.id")
    @Mapping(target = "mediaId", source = "media.id")
    @Mapping(target = "sort", source = "displayOrder")
    @Mapping(target = "altText", source = "caption")
    ArticleImageResponse toResponse(ArticleImage entity);

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "article", ignore = true)
    @Mapping(target = "media", ignore = true)
    @Mapping(target = "caption", source = "altText")
    @Mapping(target = "displayOrder", source = "sort")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntity(@MappingTarget ArticleImage entity, ArticleImageRequest request);

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "article", ignore = true)
    @Mapping(target = "media", ignore = true)
    @Mapping(target = "caption", source = "altText")
    @Mapping(target = "displayOrder", source = "sort")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void patchEntity(@MappingTarget ArticleImage entity, ArticleImageRequest request);
}
