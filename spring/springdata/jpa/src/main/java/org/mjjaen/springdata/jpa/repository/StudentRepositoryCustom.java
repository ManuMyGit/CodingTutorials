package org.mjjaen.springdata.jpa.repository;

import org.mjjaen.springdata.jpa.businessObject.Gender;
import org.mjjaen.springdata.jpa.businessObject.Student;

import java.util.List;

public interface StudentRepositoryCustom {
    List<Student> customFindByGender(Gender gender);
    List<Student> customFindByGenderQueryDSL(Gender gender);
}
