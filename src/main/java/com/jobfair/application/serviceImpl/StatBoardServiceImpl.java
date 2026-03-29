package com.jobfair.application.serviceImpl;

import com.jobfair.api.dto.request.StatBoardRequest;
import com.jobfair.api.dto.response.StatBoardResponse;
import com.jobfair.domain.model.StatBoard;
import com.jobfair.domain.repository.StatBoardRepository;
import com.jobfair.domain.service.StatBoardService;
import com.jobfair.infrastructure.mapper.StatBoardMapper;
import com.jobfair.shared.exception.ResourceNotFoundException;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StatBoardServiceImpl
        extends AbstractCrudService<StatBoard, String, StatBoardRequest, StatBoardResponse>
        implements StatBoardService {

    private final StatBoardRepository repository;
    private final StatBoardMapper mapper;

    public StatBoardServiceImpl(StatBoardRepository repository, StatBoardMapper mapper) {
        super(repository, mapper);
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    @Transactional(readOnly = true)
    public StatBoardResponse getBySlug(String slug) {
        StatBoard board = repository.findBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Stat board not found with slug: " + slug));
        return mapper.toResponse(board);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StatBoardResponse> getByYear(Integer year) {
        return repository.findByYearOrderByCreatedAtDesc(year).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<StatBoardResponse> search(String q) {
        return repository.searchRich(q, null).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<StatBoardResponse> filter(String q, Integer year) {
        return repository.searchRich(q, year).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    protected String resourceName() {
        return "Stat board";
    }
}
