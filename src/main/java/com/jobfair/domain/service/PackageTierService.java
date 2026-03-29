package com.jobfair.domain.service;

import com.jobfair.api.dto.request.PackageTierRequest;
import com.jobfair.api.dto.response.PackageTierResponse;
import com.jobfair.domain.model.PackageTier;

public interface PackageTierService extends BaseCrudService<PackageTier, String, PackageTierRequest, PackageTierResponse> {
}
