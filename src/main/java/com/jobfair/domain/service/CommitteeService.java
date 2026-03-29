package com.jobfair.domain.service;

import com.jobfair.api.dto.request.CommitteeRequest;
import com.jobfair.api.dto.response.CommitteeResponse;
import com.jobfair.domain.model.Committee;

public interface CommitteeService extends BaseCrudService<Committee, String, CommitteeRequest, CommitteeResponse> {
}
