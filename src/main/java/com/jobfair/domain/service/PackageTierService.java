package com.jobfair.domain.service;

import com.jobfair.api.dto.request.PackageTierRequest;
import com.jobfair.api.dto.response.PackageTierResponse;
import com.jobfair.domain.model.PackageTier;
import java.util.List;

public interface PackageTierService extends BaseCrudService<PackageTier, String, PackageTierRequest, PackageTierResponse> {

	PackageTierResponse getByCode(String code);

	List<PackageTierResponse> search(String q);

	List<PackageTierResponse> filter(String q, String code);
}
