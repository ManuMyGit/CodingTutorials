package org.mjjaen.springdata.jpa.repository.impl;

import org.mjjaen.springdata.jpa.repository.CourseRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;

public class CourseRepositoryCustomImpl implements CourseRepositoryCustom {
    @Autowired
    private EntityManager entityManager;
}
