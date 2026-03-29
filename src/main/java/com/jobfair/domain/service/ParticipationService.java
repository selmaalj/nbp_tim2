package com.jobfair.domain.service;

import com.jobfair.api.dto.request.ParticipationRequest;
import com.jobfair.api.dto.response.ParticipationResponse;
import com.jobfair.domain.model.Participation;
import java.util.List;

public interface ParticipationService extends BaseCrudService<Participation, String, ParticipationRequest, ParticipationResponse> {

	List<ParticipationResponse> getByYear(Integer year);

	List<ParticipationResponse> getByOrganizationId(String organizationId);

	List<ParticipationResponse> filter(Integer year, String organizationId, String packageTierId);
}
