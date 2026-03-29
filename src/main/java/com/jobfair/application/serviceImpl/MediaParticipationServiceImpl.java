package com.jobfair.application.serviceImpl;

import com.jobfair.api.dto.request.MediaParticipationRequest;
import com.jobfair.api.dto.response.MediaParticipationResponse;
import com.jobfair.domain.model.MediaParticipation;
import com.jobfair.domain.model.enums.MediaTier;
import com.jobfair.domain.repository.MediaParticipationRepository;
import com.jobfair.domain.service.MediaParticipationService;
import com.jobfair.infrastructure.mapper.MediaParticipationMapper;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MediaParticipationServiceImpl extends AbstractCrudService<MediaParticipation, String, MediaParticipationRequest, MediaParticipationResponse>
        implements MediaParticipationService {

    private final MediaParticipationRepository repository;
    private final MediaParticipationMapper mapper;

    public MediaParticipationServiceImpl(MediaParticipationRepository repository,
                                         MediaParticipationMapper mapper) {
        super(repository, mapper);
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<MediaParticipationResponse> getByYear(Integer year) {
        return repository.findByYearOrderByCreatedAtDesc(year).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<MediaParticipationResponse> getByMediaOutletId(String mediaOutletId) {
        return repository.findByOutlet_IdOrderByYearDesc(mediaOutletId).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<MediaParticipationResponse> filter(Integer year, MediaTier tier, String mediaOutletId) {
        return repository.searchRich(year, tier, mediaOutletId).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    protected String resourceName() {
        return "Media participation";
    }
}
