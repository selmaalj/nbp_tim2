package com.jobfair.application.serviceImpl;

import com.jobfair.api.dto.request.ParticipationRequest;
import com.jobfair.api.dto.response.ParticipationResponse;
import com.jobfair.domain.model.Participation;
import com.jobfair.domain.repository.ParticipationRepository;
import com.jobfair.domain.service.ParticipationService;
import com.jobfair.infrastructure.mapper.BaseMapper;
import org.springframework.stereotype.Service;

@Service
public class ParticipationServiceImpl extends AbstractCrudService<Participation, String, ParticipationRequest, ParticipationResponse>
        implements ParticipationService {

    public ParticipationServiceImpl(ParticipationRepository repository,
                                    BaseMapper<Participation, ParticipationRequest, ParticipationResponse> mapper) {
        super(repository, mapper);
    }

    @Override
    protected String resourceName() {
        return "Participation";
    }
}
