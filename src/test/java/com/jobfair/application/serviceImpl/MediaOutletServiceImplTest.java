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

import com.jobfair.api.dto.response.MediaOutletResponse;
import com.jobfair.domain.model.MediaOutlet;
import com.jobfair.domain.model.enums.MediaKind;
import com.jobfair.domain.repository.MediaOutletRepository;
import com.jobfair.infrastructure.mapper.MediaOutletMapper;
import com.jobfair.shared.exception.ResourceNotFoundException;

@ExtendWith(MockitoExtension.class)
class MediaOutletServiceImplTest {

    @Mock
    private MediaOutletRepository repository;

    @Mock
    private MediaOutletMapper mapper;

    private MediaOutletServiceImpl service;

    @BeforeEach
    @SuppressWarnings("unused")
    void setUp() {
        service = new MediaOutletServiceImpl(repository, mapper);
    }

    @Test
    void getBySlugReturnsMappedResponse() {
        MediaOutlet outlet = new MediaOutlet();
        MediaOutletResponse response = new MediaOutletResponse("1", "Outlet", "outlet", null, MediaKind.ONLINE, null);

        when(repository.findBySlug("outlet")).thenReturn(Optional.of(outlet));
        when(mapper.toResponse(outlet)).thenReturn(response);

        MediaOutletResponse result = service.getBySlug("outlet");

        assertEquals(response, result);
    }

    @Test
    void getBySlugThrowsWhenMissing() {
        when(repository.findBySlug("missing")).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> service.getBySlug("missing"));
        assertEquals("Media outlet not found with slug: missing", exception.getMessage());
    }

    @Test
    void getByKindMapsResults() {
        MediaOutlet outlet = new MediaOutlet();
        MediaOutletResponse response = new MediaOutletResponse("1", "Outlet", "outlet", null, MediaKind.PRINT, null);

        when(repository.findByKindSorted(MediaKind.PRINT)).thenReturn(List.of(outlet));
        when(mapper.toResponse(outlet)).thenReturn(response);

        List<MediaOutletResponse> result = service.getByKind(MediaKind.PRINT);

        assertEquals(List.of(response), result);
    }

    @Test
    void searchUsesRichDefaults() {
        MediaOutlet outlet = new MediaOutlet();
        when(repository.searchRich("portal", null, null)).thenReturn(List.of(outlet));
        when(mapper.toResponse(outlet)).thenReturn(new MediaOutletResponse("1", "Portal", "portal", null, MediaKind.ONLINE, null));

        service.search("portal");

        verify(repository).searchRich(eq("portal"), isNull(), isNull());
    }

    @Test
    void filterForwardsAllArguments() {
        service.filter("media", MediaKind.RADIO, true);

        verify(repository).searchRich("media", MediaKind.RADIO, true);
    }
}
