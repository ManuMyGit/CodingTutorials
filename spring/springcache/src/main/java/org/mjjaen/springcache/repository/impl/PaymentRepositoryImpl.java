package org.mjjaen.springcache.repository.impl;

import org.mjjaen.springcache.repository.PaymentRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;

public class PaymentRepositoryImpl implements PaymentRepositoryCustom {
    @Autowired
    private EntityManager entityManager;
}
