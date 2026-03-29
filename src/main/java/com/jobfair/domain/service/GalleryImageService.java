package com.jobfair.domain.service;

import com.jobfair.api.dto.request.GalleryImageRequest;
import com.jobfair.api.dto.response.GalleryImageResponse;
import com.jobfair.domain.model.GalleryImage;

public interface GalleryImageService extends BaseCrudService<GalleryImage, String, GalleryImageRequest, GalleryImageResponse> {
}
