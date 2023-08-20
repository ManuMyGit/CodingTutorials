package org.mjjaen.springcache.repository.impl;

import org.mjjaen.springcache.repository.AccountRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;

public class AccountRepositoryImpl implements AccountRepositoryCustom {
    @Autowired
    private EntityManager entityManager;
}
