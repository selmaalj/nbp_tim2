package com.jobfair.domain.service;

import com.jobfair.api.dto.request.JobRequest;
import com.jobfair.api.dto.response.JobResponse;
import com.jobfair.domain.model.Job;

public interface JobService extends BaseCrudService<Job, String, JobRequest, JobResponse> {
}
