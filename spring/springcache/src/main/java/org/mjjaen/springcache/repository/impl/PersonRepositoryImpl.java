package org.mjjaen.springcache.repository.impl;

import org.mjjaen.springcache.repository.PersonRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;

public class PersonRepositoryImpl implements PersonRepositoryCustom {
    @Autowired
    private EntityManager entityManager;
}
