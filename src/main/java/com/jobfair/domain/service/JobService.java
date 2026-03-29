package com.jobfair.domain.service;

import java.time.LocalDateTime;
import java.util.List;

import com.jobfair.api.dto.request.JobRequest;
import com.jobfair.api.dto.response.JobResponse;
import com.jobfair.domain.model.Job;

public interface JobService extends BaseCrudService<Job, String, JobRequest, JobResponse> {

	JobResponse getBySlug(String slug);

	List<JobResponse> getActive();

	List<JobResponse> search(String q);

	List<JobResponse> filter(String q, LocalDateTime postedAfter, LocalDateTime expiresBefore, boolean openOnly);
}
