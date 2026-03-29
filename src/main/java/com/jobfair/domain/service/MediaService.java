package com.jobfair.domain.service;

import com.jobfair.api.dto.request.MediaRequest;
import com.jobfair.api.dto.response.MediaResponse;
import com.jobfair.domain.model.Media;

public interface MediaService extends BaseCrudService<Media, String, MediaRequest, MediaResponse> {
}
