package com.jobfair.application.serviceImpl;

import com.jobfair.api.dto.request.CommitteeRequest;
import com.jobfair.api.dto.response.CommitteeResponse;
import com.jobfair.domain.model.Committee;
import com.jobfair.domain.repository.CommitteeRepository;
import com.jobfair.domain.service.CommitteeService;
import com.jobfair.infrastructure.mapper.CommitteeMapper;
import com.jobfair.shared.exception.ResourceNotFoundException;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommitteeServiceImpl extends AbstractCrudService<Committee, String, CommitteeRequest, CommitteeResponse>
        implements CommitteeService {

    private final CommitteeRepository repository;
    private final CommitteeMapper mapper;

    public CommitteeServiceImpl(CommitteeRepository repository,
                                CommitteeMapper mapper) {
        super(repository, mapper);
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    @Transactional(readOnly = true)
    public CommitteeResponse getByYear(Integer year) {
        Committee committee = repository.findByYear(year)
                .orElseThrow(() -> new ResourceNotFoundException("Committee not found for year: " + year));
        return mapper.toResponse(committee);
    }

    @Override
    @Transactional(readOnly = true)
    public CommitteeResponse getLatest() {
        Committee committee = repository.findTopByOrderByYearDesc()
                .orElseThrow(() -> new ResourceNotFoundException("No committees available"));
        return mapper.toResponse(committee);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommitteeResponse> search(String q) {
        return repository.searchRich(q, null, null).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommitteeResponse> filter(String q, Integer fromYear, Integer toYear) {
        return repository.searchRich(q, fromYear, toYear).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    protected String resourceName() {
        return "Committee";
    }
}
