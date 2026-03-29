package com.jobfair.application.serviceImpl;

import com.jobfair.api.dto.request.GalleryImageRequest;
import com.jobfair.api.dto.response.GalleryImageResponse;
import com.jobfair.domain.model.GalleryImage;
import com.jobfair.domain.repository.GalleryImageRepository;
import com.jobfair.domain.service.GalleryImageService;
import com.jobfair.infrastructure.mapper.GalleryImageMapper;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GalleryImageServiceImpl extends AbstractCrudService<GalleryImage, String, GalleryImageRequest, GalleryImageResponse>
        implements GalleryImageService {

    private final GalleryImageRepository repository;
    private final GalleryImageMapper mapper;

    public GalleryImageServiceImpl(GalleryImageRepository repository,
                                   GalleryImageMapper mapper) {
        super(repository, mapper);
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<GalleryImageResponse> getByOrganizationId(String organizationId) {
        return repository.findByOrganization_IdOrderByDisplayOrderAsc(organizationId).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<GalleryImageResponse> getByMediaId(String mediaId) {
        return repository.findByMedia_IdOrderByCreatedAtDesc(mediaId).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<GalleryImageResponse> filter(String organizationId, String mediaId, String q, Integer minSort, Integer maxSort) {
        return repository.searchRich(organizationId, mediaId, q, minSort, maxSort).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    protected String resourceName() {
        return "Gallery image";
    }
}
