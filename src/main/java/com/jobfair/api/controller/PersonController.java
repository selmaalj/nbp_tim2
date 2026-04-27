package com.jobfair.api.controller;

import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.jobfair.api.dto.request.PersonRequest;
import com.jobfair.api.dto.response.PersonCvFileResponse;
import com.jobfair.api.dto.response.PersonResponse;
import com.jobfair.domain.service.PersonService;
import com.jobfair.shared.constants.ApiPaths;
import com.jobfair.shared.docs.ApiResourceDocumentation;
import com.jobfair.shared.docs.DocParameter;
import com.jobfair.shared.docs.EndpointDocumentation;
import com.jobfair.shared.docs.ErrorDocProfile;
import com.jobfair.shared.docs.MultipartDocSample;
import com.jobfair.shared.response.ApiResponse;

import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@RestController
@RequestMapping(ApiPaths.PEOPLE)
@ApiResourceDocumentation(order = 130, singularName = "person", pluralName = "people", sectionTitle = "People", snippetPrefix = "people", sampleId = "person-1", description = "People directory plus CV upload/download endpoints.")
@Tag(name = "People", description = "People directory plus CV upload/download endpoints.")
@Validated
public class PersonController extends AbstractCrudController<String, PersonRequest, PersonResponse> {

    private final PersonService personService;

    public PersonController(PersonService service) {
        super(service);
        this.personService = service;
    }

    @Override
    @PostMapping
    public ResponseEntity<ApiResponse<PersonResponse>> create(@Valid @RequestBody PersonRequest request) {
        return super.create(request);
    }

    @Override
    @GetMapping
    public ResponseEntity<ApiResponse<List<PersonResponse>>> getAll() {
        return super.getAll();
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PersonResponse>> getById(@PathVariable String id) {
        return super.getById(id);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PersonResponse>> update(@PathVariable String id, @Valid @RequestBody PersonRequest request) {
        return super.update(id, request);
    }

    @Override
    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<PersonResponse>> patch(@PathVariable String id, @Valid @RequestBody PersonRequest request) {
        return super.patch(id, request);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable String id) {
        return super.delete(id);
    }

    @GetMapping("/email")
    @EndpointDocumentation(order = 70, snippetId = "people-email", displayName = "GET /people/email", summary = "Get person by email", queryParameters = @DocParameter(name = "value", value = "ana@jobfair.test"), errorProfiles = ErrorDocProfile.PEOPLE_EMAIL)
    public ResponseEntity<ApiResponse<PersonResponse>> email(@RequestParam @Email @NotBlank String value) {
        String email = value.trim();
        PersonResponse payload = personService.getByEmail(email);
        return ResponseEntity.ok(ApiResponse.success("Person by email", payload));
    }

    @GetMapping("/role")
    @EndpointDocumentation(order = 80, snippetId = "people-role", displayName = "GET /people/role", summary = "Get people by role", queryParameters = @DocParameter(name = "value", value = "Coordinator"))
    public ResponseEntity<ApiResponse<List<PersonResponse>>> role(@RequestParam @NotBlank String value) {
        List<PersonResponse> payload = personService.getByPosition(value.trim());
        return ResponseEntity.ok(ApiResponse.success("People by role", payload));
    }

    @GetMapping(params = "q")
    @EndpointDocumentation(order = 90, snippetId = "people-search", displayName = "GET /people?q=...", summary = "Search people", queryParameters = @DocParameter(name = "q", value = "ana"))
    public ResponseEntity<ApiResponse<List<PersonResponse>>> search(@RequestParam("q") @NotBlank String q) {
        List<PersonResponse> payload = personService.search(q.trim());
        return ResponseEntity.ok(ApiResponse.success("People search", payload));
    }

    @GetMapping("/filter")
    @EndpointDocumentation(order = 100, snippetId = "people-filter", displayName = "GET /people/filter", summary = "Filter people", queryParameters = {
            @DocParameter(name = "q", value = "ana"),
            @DocParameter(name = "role", value = "Coordinator"),
            @DocParameter(name = "hasEmail", value = "true")
    }, errorProfiles = ErrorDocProfile.TYPE_MISMATCH)
    public ResponseEntity<ApiResponse<List<PersonResponse>>> filter(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) String role,
            @RequestParam(required = false) Boolean hasEmail
    ) {
        List<PersonResponse> payload = personService.filter(trimToNull(q), trimToNull(role), hasEmail);
        return ResponseEntity.ok(ApiResponse.success("People filter", payload));
    }

    @PostMapping(value = "/{id}/cv", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @EndpointDocumentation(order = 110, snippetId = "people-upload-cv", displayName = "POST /people/{id}/cv", summary = "Upload CV PDF for person", expectedStatus = HttpStatus.CREATED, pathParameters = @DocParameter(name = "id", value = "person-1"), multipart = @MultipartDocSample(enabled = true, fieldName = "file", fileName = "resume.pdf", contentType = MediaType.APPLICATION_PDF_VALUE, content = "sample-pdf"), errorProfiles = ErrorDocProfile.CV_UPLOAD)
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "CV uploaded successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid file"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Person not found")
    })
    public ResponseEntity<ApiResponse<Void>> uploadCv(@PathVariable String id, @RequestPart("file") MultipartFile file) {
        personService.uploadCv(id, file);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("CV uploaded successfully", null));
    }

    @GetMapping(value = "/{id}/cv", produces = {MediaType.APPLICATION_OCTET_STREAM_VALUE, MediaType.APPLICATION_PDF_VALUE})
    @EndpointDocumentation(order = 120, snippetId = "people-get-cv", displayName = "GET /people/{id}/cv", summary = "Download person CV as PDF", pathParameters = @DocParameter(name = "id", value = "person-1"), binaryResponse = true, errorProfiles = ErrorDocProfile.CV_DOWNLOAD)
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                description = "Returns downloadable CV PDF",
                content = {
                    @Content(mediaType = MediaType.APPLICATION_PDF_VALUE, schema = @Schema(type = "string", format = "binary")),
                    @Content(mediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE, schema = @Schema(type = "string", format = "binary"))
                },
                headers = {
                    @Header(name = HttpHeaders.CONTENT_DISPOSITION, description = "Attachment filename", schema = @Schema(type = "string"))
                }
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Person or CV not found")
    })
    public ResponseEntity<byte[]> getCv(@PathVariable String id) {
        PersonCvFileResponse cvFile = personService.getCv(id);
        String safeFileName = cvFile.fileName() == null ? ("cv-" + id + ".pdf") : cvFile.fileName().replace("\"", "");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentLength(cvFile.content().length);
        headers.setContentDisposition(ContentDisposition.attachment().filename(safeFileName, StandardCharsets.UTF_8).build());

        if (cvFile.mimeType() != null && !cvFile.mimeType().isBlank()) {
            headers.add("X-Original-Content-Type", cvFile.mimeType());
        }

        return new ResponseEntity<>(cvFile.content(), headers, HttpStatus.OK);
    }

    private String trimToNull(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }

    @Override
    protected String resourceName() {
        return "Person";
    }

    @Override
    protected String resourceNamePlural() {
        return "People";
    }
}
