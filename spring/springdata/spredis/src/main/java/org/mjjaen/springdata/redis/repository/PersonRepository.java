package org.mjjaen.springdata.redis.repository;

import org.mjjaen.springdata.redis.businessObject.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends CrudRepository<Person, String> {
}
