package com.jobfair.application.serviceImpl;

import com.jobfair.api.dto.request.MediaParticipationRequest;
import com.jobfair.api.dto.response.MediaParticipationResponse;
import com.jobfair.domain.model.MediaParticipation;
import com.jobfair.domain.repository.MediaParticipationRepository;
import com.jobfair.domain.service.MediaParticipationService;
import com.jobfair.infrastructure.mapper.BaseMapper;
import org.springframework.stereotype.Service;

@Service
public class MediaParticipationServiceImpl extends AbstractCrudService<MediaParticipation, String, MediaParticipationRequest, MediaParticipationResponse>
        implements MediaParticipationService {

    public MediaParticipationServiceImpl(MediaParticipationRepository repository,
                                         BaseMapper<MediaParticipation, MediaParticipationRequest, MediaParticipationResponse> mapper) {
        super(repository, mapper);
    }

    @Override
    protected String resourceName() {
        return "Media participation";
    }
}
