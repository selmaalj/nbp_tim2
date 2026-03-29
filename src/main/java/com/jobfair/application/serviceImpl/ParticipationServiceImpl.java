package com.jobfair.application.serviceImpl;

import com.jobfair.api.dto.request.ParticipationRequest;
import com.jobfair.api.dto.response.ParticipationResponse;
import com.jobfair.domain.model.Participation;
import com.jobfair.domain.repository.ParticipationRepository;
import com.jobfair.domain.service.ParticipationService;
import com.jobfair.infrastructure.mapper.ParticipationMapper;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ParticipationServiceImpl extends AbstractCrudService<Participation, String, ParticipationRequest, ParticipationResponse>
        implements ParticipationService {

    private final ParticipationRepository repository;
    private final ParticipationMapper mapper;

    public ParticipationServiceImpl(ParticipationRepository repository,
                                    ParticipationMapper mapper) {
        super(repository, mapper);
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ParticipationResponse> getByYear(Integer year) {
        return repository.findByYearOrderByCreatedAtDesc(year).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ParticipationResponse> getByOrganizationId(String organizationId) {
        return repository.findByOrganization_IdOrderByYearDesc(organizationId).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ParticipationResponse> filter(Integer year, String organizationId, String packageTierId) {
        return repository.searchRich(year, organizationId, packageTierId).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    protected String resourceName() {
        return "Participation";
    }
}
