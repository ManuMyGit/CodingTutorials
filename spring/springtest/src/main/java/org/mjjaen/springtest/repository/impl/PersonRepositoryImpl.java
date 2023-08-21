package org.mjjaen.springtest.repository.impl;

import lombok.RequiredArgsConstructor;
import org.mjjaen.springtest.repository.PersonRepositoryCustom;

import javax.persistence.EntityManager;

@RequiredArgsConstructor
public class PersonRepositoryImpl implements PersonRepositoryCustom {
    private final EntityManager entityManager;
}
