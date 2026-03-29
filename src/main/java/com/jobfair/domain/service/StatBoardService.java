package com.jobfair.domain.service;

import com.jobfair.api.dto.request.StatBoardRequest;
import com.jobfair.api.dto.response.StatBoardResponse;
import com.jobfair.domain.model.StatBoard;

public interface StatBoardService extends BaseCrudService<StatBoard, String, StatBoardRequest, StatBoardResponse> {
}
