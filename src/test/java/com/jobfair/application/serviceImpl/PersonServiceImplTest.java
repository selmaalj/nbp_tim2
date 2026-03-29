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

import com.jobfair.api.dto.response.PersonResponse;
import com.jobfair.domain.model.Person;
import com.jobfair.domain.repository.PersonRepository;
import com.jobfair.infrastructure.mapper.PersonMapper;
import com.jobfair.shared.exception.ResourceNotFoundException;

@ExtendWith(MockitoExtension.class)
class PersonServiceImplTest {

    @Mock
    private PersonRepository repository;

    @Mock
    private PersonMapper mapper;

    private PersonServiceImpl service;

    @BeforeEach
    @SuppressWarnings("unused")
    void setUp() {
        service = new PersonServiceImpl(repository, mapper);
    }

    @Test
    void getByEmailReturnsMappedResponse() {
        Person person = new Person();
        PersonResponse response = new PersonResponse("1", "Ana", "Anic", "ana@mail.com", null, null, null);

        when(repository.findByEmailIgnoreCase("ana@mail.com")).thenReturn(Optional.of(person));
        when(mapper.toResponse(person)).thenReturn(response);

        PersonResponse result = service.getByEmail("ana@mail.com");

        assertEquals(response, result);
    }

    @Test
    void getByEmailThrowsWhenMissing() {
        when(repository.findByEmailIgnoreCase("missing@mail.com")).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> service.getByEmail("missing@mail.com"));
        assertEquals("Person not found with email: missing@mail.com", exception.getMessage());
    }

    @Test
    void getByPositionUsesRoleQuery() {
        Person person = new Person();
        PersonResponse response = new PersonResponse("1", "A", "B", null, null, "Coordinator", null);

        when(repository.findByRole("Coordinator")).thenReturn(List.of(person));
        when(mapper.toResponse(person)).thenReturn(response);

        List<PersonResponse> result = service.getByPosition("Coordinator");

        assertEquals(List.of(response), result);
    }

    @Test
    void searchUsesRichDefaults() {
        Person person = new Person();
        when(repository.searchRich("selma", null, null)).thenReturn(List.of(person));
        when(mapper.toResponse(person)).thenReturn(new PersonResponse("1", "Selma", "A", null, null, null, null));

        service.search("selma");

        verify(repository).searchRich(eq("selma"), isNull(), isNull());
    }

    @Test
    void filterForwardsAllArguments() {
        service.filter("sel", "manager", true);

        verify(repository).searchRich("sel", "manager", true);
    }
}
