package com.jobfair.api.controller;

import com.jobfair.api.dto.request.PackageTierRequest;
import com.jobfair.api.dto.response.PackageTierResponse;
import com.jobfair.domain.service.PackageTierService;
import com.jobfair.shared.constants.ApiPaths;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiPaths.PACKAGE_TIERS)
@Tag(name = "Package tiers")
public class PackageTierController extends AbstractCrudController<String, PackageTierRequest, PackageTierResponse> {

    public PackageTierController(PackageTierService service) {
        super(service);
    }

    @Override
    protected String resourceName() {
        return "Package tier";
    }

    @Override
    protected String resourceNamePlural() {
        return "Package tiers";
    }
}
