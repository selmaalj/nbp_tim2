package com.jobfair.application.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jobfair.api.dto.request.MediaOutletRequest;
import com.jobfair.api.dto.response.MediaOutletResponse;
import com.jobfair.domain.model.MediaOutlet;
import com.jobfair.domain.model.enums.MediaKind;
import com.jobfair.domain.repository.MediaOutletRepository;
import com.jobfair.domain.service.MediaOutletService;
import com.jobfair.infrastructure.mapper.MediaOutletMapper;
import com.jobfair.shared.exception.ResourceNotFoundException;

@Service
public class MediaOutletServiceImpl
        extends AbstractCrudService<MediaOutlet, String, MediaOutletRequest, MediaOutletResponse>
        implements MediaOutletService {

    private final MediaOutletRepository repository;
    private final MediaOutletMapper mapper;

    public MediaOutletServiceImpl(MediaOutletRepository repository, MediaOutletMapper mapper) {
        super(repository, mapper);
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    @Transactional(readOnly = true)
    public MediaOutletResponse getBySlug(String slug) {
        MediaOutlet outlet = repository.findBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Media outlet not found with slug: " + slug));
        return mapper.toResponse(outlet);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MediaOutletResponse> getByKind(MediaKind kind) {
        return repository.findByKindSorted(kind).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<MediaOutletResponse> search(String q) {
        return repository.searchRich(q, null, null).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<MediaOutletResponse> filter(String q, MediaKind kind, Boolean hasWebsite) {
        return repository.searchRich(q, kind, hasWebsite).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    protected String resourceName() {
        return "MediaOutlet";
    }
}
