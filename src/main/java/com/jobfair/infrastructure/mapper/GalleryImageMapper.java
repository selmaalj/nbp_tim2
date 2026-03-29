package com.jobfair.infrastructure.mapper;

import com.jobfair.api.dto.request.GalleryImageRequest;
import com.jobfair.api.dto.response.GalleryImageResponse;
import com.jobfair.domain.model.GalleryImage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface GalleryImageMapper extends BaseMapper<GalleryImage, GalleryImageRequest, GalleryImageResponse> {

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "organization", ignore = true)
    @Mapping(target = "media", ignore = true)
    @Mapping(target = "caption", source = "altText")
    @Mapping(target = "displayOrder", source = "sort")
    GalleryImage toEntity(GalleryImageRequest request);

    @Override
    @Mapping(target = "organizationId", source = "organization.id")
    @Mapping(target = "mediaId", source = "media.id")
    @Mapping(target = "sort", source = "displayOrder")
    @Mapping(target = "altText", source = "caption")
    GalleryImageResponse toResponse(GalleryImage entity);

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "organization", ignore = true)
    @Mapping(target = "media", ignore = true)
    @Mapping(target = "caption", source = "altText")
    @Mapping(target = "displayOrder", source = "sort")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntity(@MappingTarget GalleryImage entity, GalleryImageRequest request);

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "organization", ignore = true)
    @Mapping(target = "media", ignore = true)
    @Mapping(target = "caption", source = "altText")
    @Mapping(target = "displayOrder", source = "sort")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void patchEntity(@MappingTarget GalleryImage entity, GalleryImageRequest request);
}
