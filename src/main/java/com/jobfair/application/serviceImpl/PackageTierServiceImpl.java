package com.jobfair.application.serviceImpl;

import com.jobfair.api.dto.request.PackageTierRequest;
import com.jobfair.api.dto.response.PackageTierResponse;
import com.jobfair.domain.model.PackageTier;
import com.jobfair.domain.repository.PackageTierRepository;
import com.jobfair.domain.service.PackageTierService;
import com.jobfair.infrastructure.mapper.PackageTierMapper;
import com.jobfair.shared.exception.ResourceNotFoundException;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PackageTierServiceImpl extends AbstractCrudService<PackageTier, String, PackageTierRequest, PackageTierResponse>
        implements PackageTierService {

    private final PackageTierRepository repository;
    private final PackageTierMapper mapper;

    public PackageTierServiceImpl(PackageTierRepository repository,
                                  PackageTierMapper mapper) {
        super(repository, mapper);
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    @Transactional(readOnly = true)
    public PackageTierResponse getByCode(String code) {
        PackageTier tier = repository.findByTierCodeIgnoreCase(code)
                .orElseThrow(() -> new ResourceNotFoundException("Package tier not found with code: " + code));
        return mapper.toResponse(tier);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PackageTierResponse> search(String q) {
        return repository.searchRich(q, null).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PackageTierResponse> filter(String q, String code) {
        return repository.searchRich(q, code).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    protected String resourceName() {
        return "Package tier";
    }
}
