package com.jobfair.domain.service;

import com.jobfair.api.dto.request.GalleryImageRequest;
import com.jobfair.api.dto.response.GalleryImageResponse;
import com.jobfair.domain.model.GalleryImage;
import java.util.List;

public interface GalleryImageService extends BaseCrudService<GalleryImage, String, GalleryImageRequest, GalleryImageResponse> {

	List<GalleryImageResponse> getByOrganizationId(String organizationId);

	List<GalleryImageResponse> getByMediaId(String mediaId);

	List<GalleryImageResponse> filter(String organizationId, String mediaId, String q, Integer minSort, Integer maxSort);
}
