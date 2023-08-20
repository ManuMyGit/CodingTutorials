package org.mjjaen.springcache.repository.impl;

import org.mjjaen.springcache.repository.AddressRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;

public class AddressRepositoryImpl implements AddressRepositoryCustom {
    @Autowired
    private EntityManager entityManager;
}
