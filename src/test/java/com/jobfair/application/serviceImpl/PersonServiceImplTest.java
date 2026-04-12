package com.jobfair.application.serviceImpl;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import com.jobfair.api.dto.response.PersonCvFileResponse;
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

    @Test
    void uploadCvStoresPdfContentAndMetadata() throws Exception {
        Person person = new Person();
        byte[] cvBytes = new byte[]{10, 20, 30};
        MultipartFile file = Mockito.mock(MultipartFile.class);

        when(repository.findById("person-1")).thenReturn(Optional.of(person));
        when(file.isEmpty()).thenReturn(false);
        when(file.getContentType()).thenReturn(MediaType.APPLICATION_PDF_VALUE);
        when(file.getOriginalFilename()).thenReturn("resume.pdf");
        when(file.getBytes()).thenReturn(cvBytes);

        service.uploadCv("person-1", file);

        assertArrayEquals(cvBytes, person.getCvData());
        assertEquals("resume.pdf", person.getCvFileName());
        assertEquals(MediaType.APPLICATION_PDF_VALUE, person.getCvMimeType());
        verify(repository).save(person);
    }

    @Test
    void uploadCvRejectsNonPdfFile() {
        MultipartFile file = Mockito.mock(MultipartFile.class);
        when(file.isEmpty()).thenReturn(false);
        when(file.getContentType()).thenReturn(MediaType.TEXT_PLAIN_VALUE);
        when(file.getOriginalFilename()).thenReturn("resume.txt");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> service.uploadCv("person-1", file));
        assertEquals("Only PDF files are allowed for CV upload", exception.getMessage());
    }

    @Test
    void uploadCvRejectsNullFile() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> service.uploadCv("person-1", null));
        assertEquals("CV file is required", exception.getMessage());
    }

    @Test
    void uploadCvRejectsEmptyFile() {
        MultipartFile file = Mockito.mock(MultipartFile.class);
        when(file.isEmpty()).thenReturn(true);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> service.uploadCv("person-1", file));
        assertEquals("CV file is required", exception.getMessage());
    }

    @Test
    void uploadCvAcceptsPdfExtensionEvenWhenMimeTypeMissing() throws Exception {
        Person person = new Person();
        byte[] cvBytes = new byte[]{7, 8};
        MultipartFile file = Mockito.mock(MultipartFile.class);

        when(repository.findById("person-1")).thenReturn(Optional.of(person));
        when(file.isEmpty()).thenReturn(false);
        when(file.getContentType()).thenReturn(null);
        when(file.getOriginalFilename()).thenReturn("resume.pdf");
        when(file.getBytes()).thenReturn(cvBytes);

        service.uploadCv("person-1", file);

        assertArrayEquals(cvBytes, person.getCvData());
        assertEquals("resume.pdf", person.getCvFileName());
        assertEquals(MediaType.APPLICATION_PDF_VALUE, person.getCvMimeType());
    }

    @Test
    void uploadCvAddsPdfExtensionWhenMissing() throws Exception {
        Person person = new Person();
        MultipartFile file = Mockito.mock(MultipartFile.class);

        when(repository.findById("person-1")).thenReturn(Optional.of(person));
        when(file.isEmpty()).thenReturn(false);
        when(file.getContentType()).thenReturn(MediaType.APPLICATION_PDF_VALUE);
        when(file.getOriginalFilename()).thenReturn("resume");
        when(file.getBytes()).thenReturn(new byte[]{1});

        service.uploadCv("person-1", file);

        assertEquals("resume.pdf", person.getCvFileName());
    }

    @Test
    void uploadCvUsesDefaultFilenameWhenOriginalNameMissing() throws Exception {
        Person person = new Person();
        MultipartFile file = Mockito.mock(MultipartFile.class);

        when(repository.findById("person-1")).thenReturn(Optional.of(person));
        when(file.isEmpty()).thenReturn(false);
        when(file.getContentType()).thenReturn(MediaType.APPLICATION_PDF_VALUE);
        when(file.getOriginalFilename()).thenReturn("  ");
        when(file.getBytes()).thenReturn(new byte[]{1});

        service.uploadCv("person-1", file);

        assertEquals("cv-person-1.pdf", person.getCvFileName());
    }

    @Test
    void uploadCvSanitizesQuotedFilename() throws Exception {
        Person person = new Person();
        MultipartFile file = Mockito.mock(MultipartFile.class);

        when(repository.findById("person-1")).thenReturn(Optional.of(person));
        when(file.isEmpty()).thenReturn(false);
        when(file.getContentType()).thenReturn(MediaType.APPLICATION_PDF_VALUE);
        when(file.getOriginalFilename()).thenReturn("my\"resume");
        when(file.getBytes()).thenReturn(new byte[]{1});

        service.uploadCv("person-1", file);

        assertEquals("myresume.pdf", person.getCvFileName());
    }

    @Test
    void uploadCvThrowsWhenPersonMissing() {
        MultipartFile file = Mockito.mock(MultipartFile.class);
        when(file.isEmpty()).thenReturn(false);
        when(file.getContentType()).thenReturn(MediaType.APPLICATION_PDF_VALUE);
        when(file.getOriginalFilename()).thenReturn("resume.pdf");
        when(repository.findById("person-1")).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> service.uploadCv("person-1", file));
        assertEquals("Person not found with id: person-1", exception.getMessage());
    }

    @Test
    void uploadCvThrowsWhenFileReadFails() throws Exception {
        MultipartFile file = Mockito.mock(MultipartFile.class);

        when(file.isEmpty()).thenReturn(false);
        when(file.getContentType()).thenReturn(MediaType.APPLICATION_PDF_VALUE);
        when(file.getOriginalFilename()).thenReturn("resume.pdf");
        when(file.getBytes()).thenThrow(new java.io.IOException("boom"));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> service.uploadCv("person-1", file));
        assertEquals("Unable to read uploaded CV file", exception.getMessage());
    }

    @Test
    void getCvReturnsStoredCvFile() {
        Person person = new Person();
        person.setCvData(new byte[]{1, 2, 3});
        person.setCvFileName("candidate.pdf");
        person.setCvMimeType(MediaType.APPLICATION_PDF_VALUE);

        when(repository.findById("person-1")).thenReturn(Optional.of(person));

        PersonCvFileResponse result = service.getCv("person-1");

        assertEquals("candidate.pdf", result.fileName());
        assertEquals(MediaType.APPLICATION_PDF_VALUE, result.mimeType());
        assertArrayEquals(new byte[]{1, 2, 3}, result.content());
    }

    @Test
    void getCvThrowsWhenCvMissing() {
        Person person = new Person();
        when(repository.findById("person-1")).thenReturn(Optional.of(person));

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> service.getCv("person-1"));
        assertEquals("CV not found for person with id: person-1", exception.getMessage());
    }

    @Test
    void getCvFallsBackToDefaultMimeAndFilename() {
        Person person = new Person();
        person.setCvData(new byte[]{9, 9});
        person.setCvFileName(" ");
        person.setCvMimeType(" ");
        when(repository.findById("person-1")).thenReturn(Optional.of(person));

        PersonCvFileResponse result = service.getCv("person-1");

        assertEquals("cv-person-1.pdf", result.fileName());
        assertEquals(MediaType.APPLICATION_PDF_VALUE, result.mimeType());
    }

    @Test
    void getCvThrowsWhenPersonMissing() {
        when(repository.findById("person-1")).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> service.getCv("person-1"));
        assertEquals("Person not found with id: person-1", exception.getMessage());
    }
}
