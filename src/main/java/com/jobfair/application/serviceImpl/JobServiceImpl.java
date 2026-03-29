package com.jobfair.application.serviceImpl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jobfair.api.dto.request.JobRequest;
import com.jobfair.api.dto.response.JobResponse;
import com.jobfair.domain.model.Job;
import com.jobfair.domain.repository.JobRepository;
import com.jobfair.domain.service.JobService;
import com.jobfair.infrastructure.mapper.JobMapper;
import com.jobfair.shared.exception.ResourceNotFoundException;

@Service
public class JobServiceImpl
        extends AbstractCrudService<Job, String, JobRequest, JobResponse>
        implements JobService {

    private final JobRepository repository;
    private final JobMapper mapper;

    public JobServiceImpl(JobRepository repository, JobMapper mapper) {
        super(repository, mapper);
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    @Transactional(readOnly = true)
    public JobResponse getBySlug(String slug) {
        Job job = repository.findBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found with slug: " + slug));
        return mapper.toResponse(job);
    }

    @Override
    @Transactional(readOnly = true)
    public List<JobResponse> getActive() {
        return repository.findActive(LocalDateTime.now()).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<JobResponse> search(String q) {
        return repository.searchRich(q, null, null, false, LocalDateTime.now()).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<JobResponse> filter(String q, LocalDateTime postedAfter, LocalDateTime expiresBefore, boolean openOnly) {
        return repository.searchRich(q, postedAfter, expiresBefore, openOnly, LocalDateTime.now()).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    protected String resourceName() {
        return "Job";
    }
}
