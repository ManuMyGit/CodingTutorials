package org.mjjaen.mongo.repository.impl;

import org.mjjaen.mongo.repository.UserRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

public class UserRepositoryImpl implements UserRepositoryCustom {
    @Autowired
    private MongoTemplate mongoTemplate;
}
