package com.jobfair.application.serviceImpl;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.jobfair.api.dto.response.OrganizationResponse;
import com.jobfair.domain.model.Organization;
import com.jobfair.domain.model.enums.OrganizationType;
import com.jobfair.domain.repository.OrganizationRepository;
import com.jobfair.infrastructure.mapper.OrganizationMapper;
import com.jobfair.shared.exception.ResourceNotFoundException;

@ExtendWith(MockitoExtension.class)
class OrganizationServiceImplTest {

    @Mock
    private OrganizationRepository repository;

    @Mock
    private OrganizationMapper mapper;

    private OrganizationServiceImpl service;

    @BeforeEach
    @SuppressWarnings("unused")
    void setUp() {
        service = new OrganizationServiceImpl(repository, mapper);
    }

    @Test
    void getBySlugReturnsMappedResponse() {
        Organization organization = new Organization();
        OrganizationResponse response = new OrganizationResponse("1", OrganizationType.COMPANY, "Acme", "acme", null, null, null);

        when(repository.findBySlug("acme")).thenReturn(Optional.of(organization));
        when(mapper.toResponse(organization)).thenReturn(response);

        OrganizationResponse result = service.getBySlug("acme");

        assertEquals(response, result);
    }

    @Test
    void getBySlugThrowsWhenMissing() {
        when(repository.findBySlug("missing")).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> service.getBySlug("missing"));
        assertEquals("Organization not found with slug: missing", exception.getMessage());
    }

    @Test
    void getByTypeMapsResults() {
        Organization organization = new Organization();
        OrganizationResponse response = new OrganizationResponse("1", OrganizationType.NGO, "NGO", "ngo", null, null, null);

        when(repository.findByTypeSorted(OrganizationType.NGO)).thenReturn(List.of(organization));
        when(mapper.toResponse(organization)).thenReturn(response);

        List<OrganizationResponse> result = service.getByType(OrganizationType.NGO);

        assertEquals(List.of(response), result);
    }

    @Test
    void searchUsesRichDefaults() {
        Organization organization = new Organization();
        when(repository.searchRich("academy", null, null)).thenReturn(List.of(organization));
        when(mapper.toResponse(organization)).thenReturn(new OrganizationResponse("1", OrganizationType.INSTITUTION, "Academy", "academy", null, null, null));

        service.search("academy");

        verify(repository).searchRich(eq("academy"), isNull(), isNull());
    }

    @Test
    void filterForwardsAllArguments() {
        service.filter("ac", OrganizationType.COMPANY, true);

        verify(repository).searchRich("ac", OrganizationType.COMPANY, true);
    }
}
