package org.mjjaen.mongo.repository.impl;

import org.mjjaen.mongo.businessObject.Book;
import org.mjjaen.mongo.repository.BookRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

public class BookRepositoryImpl implements BookRepositoryCustom {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void add(Book... books) {
        Arrays.asList(books).stream().forEach(book -> {
            mongoTemplate.save(book);
        });
    }
}
