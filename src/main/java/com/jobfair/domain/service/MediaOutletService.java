package com.jobfair.domain.service;

import com.jobfair.api.dto.request.MediaOutletRequest;
import com.jobfair.api.dto.response.MediaOutletResponse;
import com.jobfair.domain.model.MediaOutlet;

public interface MediaOutletService extends BaseCrudService<MediaOutlet, String, MediaOutletRequest, MediaOutletResponse> {
}
