package com.jobfair.application.serviceImpl;

import com.jobfair.api.dto.request.GalleryImageRequest;
import com.jobfair.api.dto.response.GalleryImageResponse;
import com.jobfair.domain.model.GalleryImage;
import com.jobfair.domain.repository.GalleryImageRepository;
import com.jobfair.domain.service.GalleryImageService;
import com.jobfair.infrastructure.mapper.BaseMapper;
import org.springframework.stereotype.Service;

@Service
public class GalleryImageServiceImpl extends AbstractCrudService<GalleryImage, String, GalleryImageRequest, GalleryImageResponse>
        implements GalleryImageService {

    public GalleryImageServiceImpl(GalleryImageRepository repository,
                                   BaseMapper<GalleryImage, GalleryImageRequest, GalleryImageResponse> mapper) {
        super(repository, mapper);
    }

    @Override
    protected String resourceName() {
        return "Gallery image";
    }
}
