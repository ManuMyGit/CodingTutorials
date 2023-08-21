package org.mjjaen.springtest.service;

import org.mjjaen.springtest.businessObject.Person;

import java.util.List;
import java.util.Optional;

public interface PersonService {
    Optional<Person> findByFirstName(String firstName, boolean ignoreCaseAndLike);
    List<Person> findAll();
    Optional<Person> findById(Integer id);
}
