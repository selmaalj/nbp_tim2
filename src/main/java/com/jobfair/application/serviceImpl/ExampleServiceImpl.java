package com.jobfair.application.serviceImpl;

import com.jobfair.api.dto.request.ExampleRequest;
import com.jobfair.api.dto.response.ExampleResponse;
import com.jobfair.domain.model.ExampleEntity;
import com.jobfair.domain.repository.ExampleRepository;
import com.jobfair.domain.service.ExampleService;
import com.jobfair.infrastructure.mapper.ExampleMapper;
import org.springframework.stereotype.Service;

@Service
public class ExampleServiceImpl
        extends AbstractCrudService<ExampleEntity, Long, ExampleRequest, ExampleResponse>
        implements ExampleService {

    public ExampleServiceImpl(ExampleRepository repository, ExampleMapper mapper) {
        super(repository, mapper);
    }

    @Override
    protected String resourceName() {
        return "ExampleEntity";
    }

    @Override
    protected void validateForCreate(ExampleRequest request) {
        validateTitle(request.title());
        validateDescription(request.description());
    }

    @Override
    protected void validateForUpdate(ExampleRequest request) {
        validateTitle(request.title());
    }

    @Override
    protected void validateAfterPatch(ExampleEntity entity) {
        validateTitle(entity.getTitle());
    }

    private void validateTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Title is required");
        }
    }

    private void validateDescription(String description) {
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Description is required when creating a new example");
        }
    }
}
