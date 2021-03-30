package org.mjjaen.springdata.jpa.service.impl;

import org.mjjaen.springdata.jpa.businessObject.Gender;
import org.mjjaen.springdata.jpa.businessObject.Student;
import org.mjjaen.springdata.jpa.repository.StudentRepository;
import org.mjjaen.springdata.jpa.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;

@Service
public class StudentServiceImpl extends BaseServiceImpl<Student, Long, StudentRepository> implements StudentService {
    @Autowired
    public StudentServiceImpl(StudentRepository repository) {
        super(repository);
    }

    @Transactional(value = Transactional.TxType.REQUIRED)
    public List<Student> findByFirstName(String firstName) {
        return this.getRepository().findByFirstName(firstName);
    }

    @Transactional(value = Transactional.TxType.REQUIRED)
    public List<Student> findByFirstNameAndLastName(String firstName, String lastName) {
        return this.getRepository().findByFirstNameAndLastName(firstName, lastName);
    }

    @Transactional(value = Transactional.TxType.REQUIRED)
    public List<Student> findByFirstNameOrLastName(String firstName, String lastName) {
        return this.getRepository().findByFirstNameOrLastName(firstName, lastName);
    }

    @Transactional(value = Transactional.TxType.REQUIRED)
    public List<Student> findByAgeBetween(Integer age1, Integer age2) {
        return this.getRepository().findByAgeBetween(age1, age2);
    }

    @Transactional(value = Transactional.TxType.REQUIRED)
    public List<Student> findByAgeLessThan(Integer age) {
        return this.getRepository().findByAgeLessThan(age);
    }

    @Transactional(value = Transactional.TxType.REQUIRED)
    public List<Student> findByAgeLessThanEqual(Integer age) {
        return this.getRepository().findByAgeLessThanEqual(age);
    }

    @Transactional(value = Transactional.TxType.REQUIRED)
    public List<Student> findByAgeGreaterThan(Integer age) {
        return this.getRepository().findByAgeGreaterThan(age);
    }

    @Transactional(value = Transactional.TxType.REQUIRED)
    public List<Student> findByAgeGreaterThanEqual(Integer age) {
        return this.getRepository().findByAgeGreaterThanEqual(age);
    }

    @Transactional(value = Transactional.TxType.REQUIRED)
    public List<Student> findByFirstNameIgnoreCaseLikeOrderByFirstNameAsc(String firstName) {
        return this.getRepository().findByFirstNameIgnoreCaseLikeOrderByFirstNameAsc(firstName);
    }

    @Transactional(value = Transactional.TxType.REQUIRED)
    public List<Student> findByFirstNameNotLike(String firstName) {
        return this.getRepository().findByFirstNameNotLike(firstName);
    }

    @Transactional(value = Transactional.TxType.REQUIRED)
    public List<Student> findByFirstNameStartsWith(String firstName) {
        return this.getRepository().findByFirstNameStartsWith(firstName);
    }

    @Transactional(value = Transactional.TxType.REQUIRED)
    public List<Student> findByLastNameEndsWith(String lastName) {
        return this.getRepository().findByLastNameEndsWith(lastName);
    }

    @Transactional(value = Transactional.TxType.REQUIRED)
    public List<Student> findByLastNameContaining(String lastName) {
        return this.getRepository().findByLastNameContaining(lastName);
    }

    @Transactional(value = Transactional.TxType.REQUIRED)
    public List<Student> findByAgeIn(Collection<Integer> ages) {
        return this.getRepository().findByAgeIn(ages);
    }

    @Transactional(value = Transactional.TxType.REQUIRED)
    public List<Student> findByGenderNamedQuery(Gender gender, String source) {
        if(source.equals(StudentService.SPRING_DATA_REPOSITORY))
            return this.getRepository().findByGender(gender);
        else if(source.equals(StudentService.CUSTOM_REPOSITORY_QUERYDSL))
            return this.getRepository().customFindByGenderQueryDSL(gender);
        else
            return this.getRepository().customFindByGender(gender);
    }
}
