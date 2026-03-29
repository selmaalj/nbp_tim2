package com.jobfair.domain.service;

import com.jobfair.api.dto.request.MediaParticipationRequest;
import com.jobfair.api.dto.response.MediaParticipationResponse;
import com.jobfair.domain.model.MediaParticipation;

public interface MediaParticipationService extends BaseCrudService<MediaParticipation, String, MediaParticipationRequest, MediaParticipationResponse> {
}
