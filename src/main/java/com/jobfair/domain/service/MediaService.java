package com.jobfair.domain.service;

import com.jobfair.api.dto.request.MediaRequest;
import com.jobfair.api.dto.response.MediaResponse;
import com.jobfair.domain.model.Media;
import java.util.List;

public interface MediaService extends BaseCrudService<Media, String, MediaRequest, MediaResponse> {

	MediaResponse getByUrl(String url);

	List<MediaResponse> getByMimeType(String mimeType);

	List<MediaResponse> search(String q);

	List<MediaResponse> filter(String q, String mimeType, Integer minSize, Integer maxSize);
}
