package org.mjjaen.mongo.repository;

import org.mjjaen.mongo.businessObject.Book;

public interface BookRepositoryCustom {
    void add(Book... books);
}
