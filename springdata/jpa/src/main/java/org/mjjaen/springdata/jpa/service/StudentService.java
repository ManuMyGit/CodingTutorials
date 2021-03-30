package org.mjjaen.springdata.jpa.service;

import org.mjjaen.springdata.jpa.businessObject.Gender;
import org.mjjaen.springdata.jpa.businessObject.Student;

import java.util.Collection;
import java.util.List;

public interface StudentService extends BaseService<Student, Long> {
    public static final String SPRING_DATA_REPOSITORY = "SpringDataRepository";
    public static final String CUSTOM_REPOSITORY = "CustomRepository";
    public static final String CUSTOM_REPOSITORY_QUERYDSL = "CustomRepository";
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
    List<Student> findByGenderNamedQuery(Gender gender, String source);
}
