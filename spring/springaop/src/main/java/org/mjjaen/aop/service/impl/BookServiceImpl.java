package org.mjjaen.aop.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mjjaen.aop.annotation.LogExecutionTime;
import org.mjjaen.aop.businessObject.Book;
import org.mjjaen.aop.repository.BookRepository;
import org.mjjaen.aop.service.BookService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    @Override
    @LogExecutionTime
    public void save(Book book) {
        log.info("Adding book: " + book);
        bookRepository.save(book);
    }

    @Override
    public Book findById(String id) {
        log.info("Retrieving book with id: " + id);
        return bookRepository.findById(id);
    }

    @Override
    public List<Book> findAll() {
        log.info("Retrieving all books");
        return bookRepository.findAll();
    }

    @Override
    public void deleteById(String id) {
        log.info("Deleting book with id: " + id);
        throw new RuntimeException("Exception to test the after exception advice");
    }

    @Override
    public void deleteAll() {
        log.info("Deleting all books");
        bookRepository.deleteAll();
    }
}
