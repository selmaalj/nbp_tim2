package com.jobfair.application.serviceImpl;

import com.jobfair.api.dto.request.MediaRequest;
import com.jobfair.api.dto.response.MediaResponse;
import com.jobfair.domain.model.Media;
import com.jobfair.domain.repository.MediaRepository;
import com.jobfair.domain.service.MediaService;
import com.jobfair.infrastructure.mapper.MediaMapper;
import com.jobfair.shared.exception.ResourceNotFoundException;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MediaServiceImpl
        extends AbstractCrudService<Media, String, MediaRequest, MediaResponse>
        implements MediaService {

    private final MediaRepository repository;
    private final MediaMapper mapper;

    public MediaServiceImpl(MediaRepository repository, MediaMapper mapper) {
        super(repository, mapper);
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    @Transactional(readOnly = true)
    public MediaResponse getByUrl(String url) {
        Media media = repository.findByUrl(url)
                .orElseThrow(() -> new ResourceNotFoundException("Media not found with url: " + url));
        return mapper.toResponse(media);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MediaResponse> getByMimeType(String mimeType) {
        return repository.findByMimeTypeIgnoreCaseOrderByCreatedAtDesc(mimeType).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<MediaResponse> search(String q) {
        return repository.searchRich(q, null, null, null).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<MediaResponse> filter(String q, String mimeType, Integer minSize, Integer maxSize) {
        return repository.searchRich(q, mimeType, minSize, maxSize).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    protected String resourceName() {
        return "Media";
    }
}
