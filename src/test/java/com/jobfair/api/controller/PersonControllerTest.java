package com.jobfair.api.controller;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.jobfair.api.dto.response.PersonCvFileResponse;
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

    @Test
    void uploadCvDelegatesToServiceAndReturnsCreated() {
        MultipartFile file = Mockito.mock(MultipartFile.class);

        ResponseEntity<ApiResponse<Void>> response = controller.uploadCv("person-1", file);
        ApiResponse<Void> body = response.getBody();

        verify(personService).uploadCv("person-1", file);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(body);
        assertEquals("CV uploaded successfully", body.message());
    }

    @Test
    void getCvReturnsPdfPayloadWithHeaders() {
        byte[] content = new byte[]{1, 2, 3};
        PersonCvFileResponse cv = new PersonCvFileResponse(content, "resume.pdf", MediaType.APPLICATION_PDF_VALUE);
        when(personService.getCv("person-1")).thenReturn(cv);

        ResponseEntity<byte[]> response = controller.getCv("person-1");

        assertEquals(MediaType.APPLICATION_OCTET_STREAM, response.getHeaders().getContentType());
        String disposition = response.getHeaders().getFirst(HttpHeaders.CONTENT_DISPOSITION);
        assertNotNull(disposition);
        org.junit.jupiter.api.Assertions.assertTrue(disposition.startsWith("attachment;"));
        org.junit.jupiter.api.Assertions.assertTrue(disposition.contains("resume.pdf"));
        assertNotNull(response.getBody());
        assertArrayEquals(content, response.getBody());
    }

    @Test
    void getCvFallsBackToDefaultFileNameAndMimeWhenMissing() {
        byte[] content = new byte[]{4, 5, 6};
        PersonCvFileResponse cv = new PersonCvFileResponse(content, null, " ");
        when(personService.getCv("person-99")).thenReturn(cv);

        ResponseEntity<byte[]> response = controller.getCv("person-99");

        assertEquals(MediaType.APPLICATION_OCTET_STREAM, response.getHeaders().getContentType());
        String disposition = response.getHeaders().getFirst(HttpHeaders.CONTENT_DISPOSITION);
        assertNotNull(disposition);
        org.junit.jupiter.api.Assertions.assertTrue(disposition.contains("cv-person-99.pdf"));
    }

    @Test
    void getCvSanitizesQuotesInFileName() {
        byte[] content = new byte[]{9};
        PersonCvFileResponse cv = new PersonCvFileResponse(content, "my\"resume.pdf", MediaType.APPLICATION_PDF_VALUE);
        when(personService.getCv("person-1")).thenReturn(cv);

        ResponseEntity<byte[]> response = controller.getCv("person-1");

        String disposition = response.getHeaders().getFirst(HttpHeaders.CONTENT_DISPOSITION);
        assertNotNull(disposition);
        org.junit.jupiter.api.Assertions.assertTrue(disposition.startsWith("attachment;"));
        org.junit.jupiter.api.Assertions.assertTrue(disposition.contains("myresume.pdf"));
    }

    @Test
    void getCvSupportsOctetStreamMimeType() {
        byte[] content = new byte[]{11, 22};
        PersonCvFileResponse cv = new PersonCvFileResponse(content, "resume.pdf", MediaType.APPLICATION_OCTET_STREAM_VALUE);
        when(personService.getCv("person-1")).thenReturn(cv);

        ResponseEntity<byte[]> response = controller.getCv("person-1");

        assertEquals(MediaType.APPLICATION_OCTET_STREAM, response.getHeaders().getContentType());
        assertArrayEquals(content, response.getBody());
    }

    @Test
    void getCvIncludesOriginalMimeTypeHeaderWhenProvided() {
        byte[] content = new byte[]{1};
        PersonCvFileResponse cv = new PersonCvFileResponse(content, "resume.pdf", MediaType.APPLICATION_PDF_VALUE);
        when(personService.getCv("person-1")).thenReturn(cv);

        ResponseEntity<byte[]> response = controller.getCv("person-1");

        assertEquals(MediaType.APPLICATION_PDF_VALUE, response.getHeaders().getFirst("X-Original-Content-Type"));
    }
}
