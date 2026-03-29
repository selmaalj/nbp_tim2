package com.jobfair.api.controller;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.http.ResponseEntity;

import com.jobfair.api.dto.response.PersonResponse;
import com.jobfair.domain.service.PersonService;
import com.jobfair.shared.response.ApiResponse;

class PersonControllerTest {

    private final PersonService personService = Mockito.mock(PersonService.class);
    private final PersonController controller = new PersonController(personService);

    @Test
    void emailTrimsInputAndReturnsPayload() {
        PersonResponse dto = new PersonResponse("1", "Ana", "Anic", "ana@mail.com", null, null, null);
        when(personService.getByEmail("ana@mail.com")).thenReturn(dto);

        ResponseEntity<ApiResponse<PersonResponse>> response = controller.email("  ana@mail.com  ");
        ApiResponse<PersonResponse> body = response.getBody();

        assertNotNull(body);
        assertEquals(dto, body.data());
        verify(personService).getByEmail("ana@mail.com");
    }

    @Test
    void roleTrimsInput() {
        controller.role("  coordinator  ");

        verify(personService).getByPosition("coordinator");
    }

    @Test
    void searchTrimsInput() {
        controller.search("  selma  ");

        verify(personService).search("selma");
    }

    @Test
    void filterConvertsBlankToNull() {
        controller.filter("   ", "  mentor  ", true);

        verify(personService).filter(null, "mentor", true);
    }

    @Test
    void roleReturnsPayload() {
        PersonResponse dto = new PersonResponse("1", "A", "B", null, null, "Coordinator", null);
        when(personService.getByPosition("Coordinator")).thenReturn(List.of(dto));

        ResponseEntity<ApiResponse<List<PersonResponse>>> response = controller.role("Coordinator");
        ApiResponse<List<PersonResponse>> body = response.getBody();

        assertNotNull(body);
        assertNotNull(body.data());
        assertEquals(1, body.data().size());
    }
}
