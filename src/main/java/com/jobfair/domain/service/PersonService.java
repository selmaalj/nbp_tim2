package com.jobfair.domain.service;

import com.jobfair.api.dto.request.PersonRequest;
import com.jobfair.api.dto.response.PersonResponse;
import com.jobfair.domain.model.Person;

public interface PersonService extends BaseCrudService<Person, String, PersonRequest, PersonResponse> {
}
