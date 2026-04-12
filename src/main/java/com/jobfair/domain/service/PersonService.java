package com.jobfair.domain.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.jobfair.api.dto.request.PersonRequest;
import com.jobfair.api.dto.response.PersonCvFileResponse;
import com.jobfair.api.dto.response.PersonResponse;
import com.jobfair.domain.model.Person;

public interface PersonService extends BaseCrudService<Person, String, PersonRequest, PersonResponse> {

	PersonResponse getByEmail(String email);

	List<PersonResponse> getByPosition(String role);

	List<PersonResponse> search(String q);

	List<PersonResponse> filter(String q, String role, Boolean hasEmail);

	void uploadCv(String personId, MultipartFile file);

	PersonCvFileResponse getCv(String personId);
}
