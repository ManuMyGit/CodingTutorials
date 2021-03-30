package org.mjjaen.springdata.jpa.repository.impl;

import org.mjjaen.springdata.jpa.repository.AddressRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;

public class AddressRepositoryCustomImpl implements AddressRepositoryCustom {
    @Autowired
    private EntityManager entityManager;
}
