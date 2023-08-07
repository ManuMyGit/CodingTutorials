package org.mjjaen.springdata.jpa.repository;

import org.mjjaen.springdata.jpa.businessObject.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface TeacherRepository extends TeacherRepositoryCustom, JpaRepository<Teacher, Long> {
    Optional<Teacher> findOptionalByFirstName(String firstName);
    Page<Teacher> findByFirstNameLike(String firstName, Pageable pageable);
    List<Teacher> findByFirstNameIn(Collection<String> firstNames, Sort sort);
    List<Teacher> findTop3ByLastNameLike(String lastName);
}
