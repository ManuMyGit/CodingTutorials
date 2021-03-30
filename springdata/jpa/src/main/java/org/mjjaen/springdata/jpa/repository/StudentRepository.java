package org.mjjaen.springdata.jpa.repository;

import org.mjjaen.springdata.jpa.businessObject.Gender;
import org.mjjaen.springdata.jpa.businessObject.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface StudentRepository extends StudentRepositoryCustom, JpaRepository<Student, Long> {
    List<Student> findByFirstName(String firstName);
    List<Student> findByFirstNameAndLastName(String firstName, String lastName);
    List<Student> findByFirstNameOrLastName(String firstName, String lastName);
    List<Student> findByAgeBetween(Integer age1, Integer age2);
    List<Student> findByAgeLessThan(Integer age);
    List<Student> findByAgeLessThanEqual(Integer age);
    List<Student> findByAgeGreaterThan(Integer age);
    List<Student> findByAgeGreaterThanEqual(Integer age);
    List<Student> findByFirstNameIgnoreCaseLikeOrderByFirstNameAsc(String firstName);
    List<Student> findByFirstNameNotLike(String firstName);
    List<Student> findByFirstNameStartsWith(String firstName);
    List<Student> findByLastNameEndsWith(String lastName);
    List<Student> findByLastNameContaining(String lastName);
    List<Student> findByAgeIn(Collection<Integer> ages);
    List<Student> findByGender(Gender gender);
}
