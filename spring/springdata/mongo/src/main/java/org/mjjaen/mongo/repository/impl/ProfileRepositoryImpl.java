package org.mjjaen.mongo.repository.impl;

import org.mjjaen.mongo.repository.ProfileRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

public class ProfileRepositoryImpl implements ProfileRepositoryCustom {
    @Autowired
    private MongoTemplate mongoTemplate;
}
