package com.jobfair.domain.service;

import com.jobfair.api.dto.request.StatBoardRequest;
import com.jobfair.api.dto.response.StatBoardResponse;
import com.jobfair.domain.model.StatBoard;
import java.util.List;

public interface StatBoardService extends BaseCrudService<StatBoard, String, StatBoardRequest, StatBoardResponse> {

	StatBoardResponse getBySlug(String slug);

	List<StatBoardResponse> getByYear(Integer year);

	List<StatBoardResponse> search(String q);

	List<StatBoardResponse> filter(String q, Integer year);
}
