package com.jobfair.application.serviceImpl;

import com.jobfair.api.dto.request.StatBoardRequest;
import com.jobfair.api.dto.response.StatBoardResponse;
import com.jobfair.domain.model.StatBoard;
import com.jobfair.domain.repository.StatBoardRepository;
import com.jobfair.domain.service.StatBoardService;
import com.jobfair.infrastructure.mapper.StatBoardMapper;
import org.springframework.stereotype.Service;

@Service
public class StatBoardServiceImpl
        extends AbstractCrudService<StatBoard, String, StatBoardRequest, StatBoardResponse>
        implements StatBoardService {

    public StatBoardServiceImpl(StatBoardRepository repository, StatBoardMapper mapper) {
        super(repository, mapper);
    }

    @Override
    protected String resourceName() {
        return "Stat board";
    }
}
