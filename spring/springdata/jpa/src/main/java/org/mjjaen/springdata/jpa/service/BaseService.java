package org.mjjaen.springdata.jpa.service;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface BaseService<T, ID extends Serializable> {
    List<T> findAll();
    Iterable<T> findAllIterable(Sort var1);
    T getOne(ID var1);
    T save(T var1);
    Optional<T> findById(ID var1);
    boolean existsById(ID var1);
    long count();
    void deleteById(ID var1);
    void delete(T var1);
    void deleteAllInBatch();
}
