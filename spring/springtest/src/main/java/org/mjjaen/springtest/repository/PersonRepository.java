package org.mjjaen.springtest.repository;

import org.mjjaen.springtest.businessObject.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer>, PersonRepositoryCustom {
    Optional<Person> findByFirstName(String firstName);
    Optional<Person> findByFirstNameContainingIgnoreCase(String firstName);
}
