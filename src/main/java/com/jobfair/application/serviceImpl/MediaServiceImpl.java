package com.jobfair.application.serviceImpl;

import com.jobfair.api.dto.request.MediaRequest;
import com.jobfair.api.dto.response.MediaResponse;
import com.jobfair.domain.model.Media;
import com.jobfair.domain.repository.MediaRepository;
import com.jobfair.domain.service.MediaService;
import com.jobfair.infrastructure.mapper.MediaMapper;
import org.springframework.stereotype.Service;

@Service
public class MediaServiceImpl
        extends AbstractCrudService<Media, String, MediaRequest, MediaResponse>
        implements MediaService {

    public MediaServiceImpl(MediaRepository repository, MediaMapper mapper) {
        super(repository, mapper);
    }

    @Override
    protected String resourceName() {
        return "Media";
    }
}
