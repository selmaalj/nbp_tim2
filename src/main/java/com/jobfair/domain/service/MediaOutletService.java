package com.jobfair.domain.service;

import java.util.List;

import com.jobfair.api.dto.request.MediaOutletRequest;
import com.jobfair.api.dto.response.MediaOutletResponse;
import com.jobfair.domain.model.MediaOutlet;
import com.jobfair.domain.model.enums.MediaKind;

public interface MediaOutletService extends BaseCrudService<MediaOutlet, String, MediaOutletRequest, MediaOutletResponse> {

	MediaOutletResponse getBySlug(String slug);

	List<MediaOutletResponse> getByKind(MediaKind kind);

	List<MediaOutletResponse> search(String q);

	List<MediaOutletResponse> filter(String q, MediaKind kind, Boolean hasWebsite);
}
