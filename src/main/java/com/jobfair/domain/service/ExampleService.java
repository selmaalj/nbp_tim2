package com.jobfair.domain.service;

import com.jobfair.api.dto.request.ExampleRequest;
import com.jobfair.api.dto.response.ExampleResponse;
import com.jobfair.domain.model.ExampleEntity;

public interface ExampleService extends BaseCrudService<ExampleEntity, Long, ExampleRequest, ExampleResponse> {
}
