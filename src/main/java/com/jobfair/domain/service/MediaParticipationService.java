package com.jobfair.domain.service;

import com.jobfair.api.dto.request.MediaParticipationRequest;
import com.jobfair.api.dto.response.MediaParticipationResponse;
import com.jobfair.domain.model.MediaParticipation;
import com.jobfair.domain.model.enums.MediaTier;
import java.util.List;

public interface MediaParticipationService extends BaseCrudService<MediaParticipation, String, MediaParticipationRequest, MediaParticipationResponse> {

	List<MediaParticipationResponse> getByYear(Integer year);

	List<MediaParticipationResponse> getByMediaOutletId(String mediaOutletId);

	List<MediaParticipationResponse> filter(Integer year, MediaTier tier, String mediaOutletId);
}
