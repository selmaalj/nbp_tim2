package com.jobfair.application.serviceImpl;

import com.jobfair.api.dto.request.MediaOutletRequest;
import com.jobfair.api.dto.response.MediaOutletResponse;
import com.jobfair.domain.model.MediaOutlet;
import com.jobfair.domain.repository.MediaOutletRepository;
import com.jobfair.domain.service.MediaOutletService;
import com.jobfair.infrastructure.mapper.MediaOutletMapper;
import org.springframework.stereotype.Service;

@Service
public class MediaOutletServiceImpl
        extends AbstractCrudService<MediaOutlet, String, MediaOutletRequest, MediaOutletResponse>
        implements MediaOutletService {

    public MediaOutletServiceImpl(MediaOutletRepository repository, MediaOutletMapper mapper) {
        super(repository, mapper);
    }

    @Override
    protected String resourceName() {
        return "MediaOutlet";
    }
}
