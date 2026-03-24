package com.jobfair.application.serviceImpl;

import com.jobfair.api.dto.request.PersonRequest;
import com.jobfair.api.dto.response.PersonResponse;
import com.jobfair.domain.model.Person;
import com.jobfair.domain.repository.PersonRepository;
import com.jobfair.domain.service.PersonService;
import com.jobfair.infrastructure.mapper.PersonMapper;
import org.springframework.stereotype.Service;

@Service
public class PersonServiceImpl
        extends AbstractCrudService<Person, String, PersonRequest, PersonResponse>
        implements PersonService {

    public PersonServiceImpl(PersonRepository repository, PersonMapper mapper) {
        super(repository, mapper);
    }

    @Override
    protected String resourceName() {
        return "Person";
    }
}
