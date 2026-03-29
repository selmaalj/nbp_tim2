package com.jobfair.api.controller;

import com.jobfair.api.dto.request.StatBoardRequest;
import com.jobfair.api.dto.response.StatBoardResponse;
import com.jobfair.domain.service.StatBoardService;
import com.jobfair.shared.constants.ApiPaths;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiPaths.STAT_BOARDS)
@Tag(name = "Stat boards")
public class StatBoardController extends AbstractCrudController<String, StatBoardRequest, StatBoardResponse> {

    public StatBoardController(StatBoardService service) {
        super(service);
    }

    @Override
    protected String resourceName() {
        return "Stat board";
    }

    @Override
    protected String resourceNamePlural() {
        return "Stat boards";
    }
}
