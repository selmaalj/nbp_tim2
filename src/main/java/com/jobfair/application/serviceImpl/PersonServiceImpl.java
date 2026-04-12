package com.jobfair.application.serviceImpl;

import java.io.IOException;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.jobfair.api.dto.request.PersonRequest;
import com.jobfair.api.dto.response.PersonCvFileResponse;
import com.jobfair.api.dto.response.PersonResponse;
import com.jobfair.domain.model.Person;
import com.jobfair.domain.repository.PersonRepository;
import com.jobfair.domain.service.PersonService;
import com.jobfair.infrastructure.mapper.PersonMapper;
import com.jobfair.shared.exception.ResourceNotFoundException;

@Service
public class PersonServiceImpl
        extends AbstractCrudService<Person, String, PersonRequest, PersonResponse>
        implements PersonService {

    private final PersonRepository repository;
    private final PersonMapper mapper;

    public PersonServiceImpl(PersonRepository repository, PersonMapper mapper) {
        super(repository, mapper);
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    @Transactional(readOnly = true)
    public PersonResponse getByEmail(String email) {
        Person person = repository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new ResourceNotFoundException("Person not found with email: " + email));
        return mapper.toResponse(person);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PersonResponse> getByPosition(String role) {
        return repository.findByRole(role).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PersonResponse> search(String q) {
        return repository.searchRich(q, null, null)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PersonResponse> filter(String q, String role, Boolean hasEmail) {
        return repository.searchRich(q, role, hasEmail)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public void uploadCv(String personId, MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("CV file is required");
        }

        String originalFileName = file.getOriginalFilename();
        String contentType = file.getContentType();
        boolean looksLikePdfByName = originalFileName != null && originalFileName.toLowerCase().endsWith(".pdf");
        boolean isPdfContentType = MediaType.APPLICATION_PDF_VALUE.equalsIgnoreCase(contentType);

        if (!looksLikePdfByName && !isPdfContentType) {
            throw new IllegalArgumentException("Only PDF files are allowed for CV upload");
        }

        byte[] content;
        try {
            content = file.getBytes();
        } catch (IOException ex) {
            throw new IllegalArgumentException("Unable to read uploaded CV file", ex);
        }

        Person person = findOrThrow(personId);
        person.setCvData(content);
        person.setCvFileName(resolveFileName(personId, originalFileName));
        person.setCvMimeType(MediaType.APPLICATION_PDF_VALUE);
        repository.save(person);
    }

    @Override
    @Transactional(readOnly = true)
    public PersonCvFileResponse getCv(String personId) {
        Person person = findOrThrow(personId);
        byte[] cvContent = person.getCvData();
        if (cvContent == null || cvContent.length == 0) {
            throw new ResourceNotFoundException("CV not found for person with id: " + personId);
        }

        String fileName = resolveFileName(personId, person.getCvFileName());
        String mimeType = person.getCvMimeType() == null || person.getCvMimeType().isBlank()
                ? MediaType.APPLICATION_PDF_VALUE
                : person.getCvMimeType();

        return new PersonCvFileResponse(cvContent, fileName, mimeType);
    }

    private String resolveFileName(String personId, String originalFileName) {
        if (originalFileName == null || originalFileName.isBlank()) {
            return "cv-" + personId + ".pdf";
        }

        String sanitized = originalFileName.replace("\"", "").trim();
        if (sanitized.isEmpty()) {
            return "cv-" + personId + ".pdf";
        }

        return sanitized.toLowerCase().endsWith(".pdf") ? sanitized : sanitized + ".pdf";
    }

    @Override
    protected String resourceName() {
        return "Person";
    }
}
