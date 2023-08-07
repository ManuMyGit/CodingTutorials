package org.mjjaen.springdata.jpa.repository.impl;

import org.mjjaen.springdata.jpa.repository.TeacherRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;

public class TeacherRepositoryCustomImpl implements TeacherRepositoryCustom {
    @Autowired
    private EntityManager entityManager;
}
