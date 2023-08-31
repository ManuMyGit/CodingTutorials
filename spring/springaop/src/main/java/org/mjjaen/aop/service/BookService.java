package org.mjjaen.aop.service;

import org.mjjaen.aop.businessObject.Book;

import java.util.List;

public interface BookService {
    void save(Book book);
    Book findById(String id);
    List<Book> findAll();
    void deleteById(String id);
    void deleteAll();
}
