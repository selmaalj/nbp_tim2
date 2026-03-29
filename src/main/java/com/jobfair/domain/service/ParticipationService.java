package com.jobfair.domain.service;

import com.jobfair.api.dto.request.ParticipationRequest;
import com.jobfair.api.dto.response.ParticipationResponse;
import com.jobfair.domain.model.Participation;

public interface ParticipationService extends BaseCrudService<Participation, String, ParticipationRequest, ParticipationResponse> {
}
