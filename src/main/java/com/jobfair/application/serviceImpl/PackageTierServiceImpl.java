package com.jobfair.application.serviceImpl;

import com.jobfair.api.dto.request.PackageTierRequest;
import com.jobfair.api.dto.response.PackageTierResponse;
import com.jobfair.domain.model.PackageTier;
import com.jobfair.domain.repository.PackageTierRepository;
import com.jobfair.domain.service.PackageTierService;
import com.jobfair.infrastructure.mapper.BaseMapper;
import org.springframework.stereotype.Service;

@Service
public class PackageTierServiceImpl extends AbstractCrudService<PackageTier, String, PackageTierRequest, PackageTierResponse>
        implements PackageTierService {

    public PackageTierServiceImpl(PackageTierRepository repository,
                                  BaseMapper<PackageTier, PackageTierRequest, PackageTierResponse> mapper) {
        super(repository, mapper);
    }

    @Override
    protected String resourceName() {
        return "Package tier";
    }
}
