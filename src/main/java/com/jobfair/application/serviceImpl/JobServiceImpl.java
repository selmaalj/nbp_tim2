package com.jobfair.application.serviceImpl;

import com.jobfair.api.dto.request.JobRequest;
import com.jobfair.api.dto.response.JobResponse;
import com.jobfair.domain.model.Job;
import com.jobfair.domain.repository.JobRepository;
import com.jobfair.domain.service.JobService;
import com.jobfair.infrastructure.mapper.JobMapper;
import org.springframework.stereotype.Service;

@Service
public class JobServiceImpl
        extends AbstractCrudService<Job, String, JobRequest, JobResponse>
        implements JobService {

    public JobServiceImpl(JobRepository repository, JobMapper mapper) {
        super(repository, mapper);
    }

    @Override
    protected String resourceName() {
        return "Job";
    }
}
