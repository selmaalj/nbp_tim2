package com.jobfair.api.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jobfair.api.dto.request.PersonRequest;
import com.jobfair.api.dto.response.PersonResponse;
import com.jobfair.domain.service.PersonService;
import com.jobfair.shared.constants.ApiPaths;
import com.jobfair.shared.response.ApiResponse;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@RestController
@RequestMapping(ApiPaths.PEOPLE)
@Tag(name = "People")
@Validated
public class PersonController extends AbstractCrudController<String, PersonRequest, PersonResponse> {

    private final PersonService personService;

    public PersonController(PersonService service) {
        super(service);
        this.personService = service;
    }

    @GetMapping("/email")
    public ResponseEntity<ApiResponse<PersonResponse>> email(@RequestParam @Email @NotBlank String value) {
        String email = value.trim();
        PersonResponse payload = personService.getByEmail(email);
        return ResponseEntity.ok(ApiResponse.success("Person by email", payload));
    }

    @GetMapping("/role")
    public ResponseEntity<ApiResponse<List<PersonResponse>>> role(@RequestParam @NotBlank String value) {
        List<PersonResponse> payload = personService.getByPosition(value.trim());
        return ResponseEntity.ok(ApiResponse.success("People by role", payload));
    }

    @GetMapping(params = "q")
    public ResponseEntity<ApiResponse<List<PersonResponse>>> search(@RequestParam("q") @NotBlank String q) {
        List<PersonResponse> payload = personService.search(q.trim());
        return ResponseEntity.ok(ApiResponse.success("People search", payload));
    }

    @GetMapping("/filter")
    public ResponseEntity<ApiResponse<List<PersonResponse>>> filter(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) String role,
            @RequestParam(required = false) Boolean hasEmail
    ) {
        List<PersonResponse> payload = personService.filter(trimToNull(q), trimToNull(role), hasEmail);
        return ResponseEntity.ok(ApiResponse.success("People filter", payload));
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
