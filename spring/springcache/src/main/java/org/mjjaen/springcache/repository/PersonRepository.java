package org.mjjaen.springcache.repository;

import org.mjjaen.springcache.businessObject.Address;
import org.mjjaen.springcache.businessObject.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.util.List;

@Repository
public interface PersonRepository extends AddressRepositoryCustom, JpaRepository<Person, Long> {
    @Nullable
    List<Person> findByFirstName(String firstName);
    @Nullable
    List<Person> findByLastName(String lastName);
    @Nullable
    List<Person> findByAddress(Address address);
}