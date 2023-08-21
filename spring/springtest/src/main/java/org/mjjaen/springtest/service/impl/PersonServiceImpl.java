package org.mjjaen.springtest.service.impl;

import lombok.RequiredArgsConstructor;
import org.mjjaen.springtest.businessObject.Person;
import org.mjjaen.springtest.repository.PersonRepository;
import org.mjjaen.springtest.service.PersonService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;

    @Override
    public Optional<Person> findByFirstName(String firstName, boolean ignoreCaseAndLike) {
        if(!ignoreCaseAndLike)
            return personRepository.findByFirstName(firstName);
        else
            return personRepository.findByFirstNameContainingIgnoreCase(firstName);
    }

    @Override
    public List<Person> findAll() {
        return personRepository.findAll();
    }

    @Override
    public Optional<Person> findById(Integer id) {
        return personRepository.findById(id);
    }
}
