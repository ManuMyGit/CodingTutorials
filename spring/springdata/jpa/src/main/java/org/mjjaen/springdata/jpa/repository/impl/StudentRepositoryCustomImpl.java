package org.mjjaen.springdata.jpa.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.mjjaen.springdata.jpa.businessObject.Gender;
import org.mjjaen.springdata.jpa.businessObject.QStudent;
import org.mjjaen.springdata.jpa.businessObject.Student;
import org.mjjaen.springdata.jpa.repository.StudentRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class StudentRepositoryCustomImpl implements StudentRepositoryCustom {
    @Autowired
    private EntityManager entityManager;

    private static final QStudent student = new QStudent("user");

    @Override
    public List<Student> customFindByGender(Gender gender) {
        Query q = entityManager.createNamedQuery("Student.findByGender");
        q.setParameter(1, gender);
        return q.getResultList();
    }

    @Override
    public List<Student> customFindByGenderQueryDSL(Gender gender) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        List<Student> studentList = queryFactory.selectFrom(student).where(student.gender.eq(gender)).orderBy(student.firstName.asc()).fetch();
        return studentList;
    }
}
