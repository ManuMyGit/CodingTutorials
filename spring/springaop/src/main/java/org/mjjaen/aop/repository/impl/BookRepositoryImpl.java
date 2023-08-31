package org.mjjaen.aop.repository.impl;

import org.mjjaen.aop.businessObject.Book;
import org.mjjaen.aop.repository.BookRepository;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class BookRepositoryImpl implements BookRepository {
    private static Map<String, Book> books = new HashMap();

    @Override
    public void save(Book book) {
        books.put(book.getId(), book);
    }

    @Override
    public Book findById(String id) {
        return books.get(id);
    }

    @Override
    public List<Book> findAll() {
        List list = new ArrayList<>();
        list.addAll(books.values());
        return list;
    }

    @Override
    public void deleteById(String id) {
        books.remove(id);
    }

    @Override
    public void deleteAll() {
        books = new HashMap<>();
    }
}
