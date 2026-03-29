package com.jobfair.domain.service;

import com.jobfair.api.dto.request.CommitteeRequest;
import com.jobfair.api.dto.response.CommitteeResponse;
import com.jobfair.domain.model.Committee;
import java.util.List;

public interface CommitteeService extends BaseCrudService<Committee, String, CommitteeRequest, CommitteeResponse> {

	CommitteeResponse getByYear(Integer year);

	CommitteeResponse getLatest();

	List<CommitteeResponse> search(String q);

	List<CommitteeResponse> filter(String q, Integer fromYear, Integer toYear);
}
