package org.mjjaen.springdata.jpa.service.impl;

import org.mjjaen.springdata.jpa.service.BaseService;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public abstract class BaseServiceImpl<T, ID extends Serializable, DAO extends JpaRepository<T, ID>> implements BaseService<T, ID> {
    private final DAO repository;

    protected BaseServiceImpl(DAO repository) {
        this.repository = repository;
    }

    @Transactional(value = Transactional.TxType.REQUIRED)
    public List<T> findAll() {
        return repository.findAll();
    }

    @Transactional(value = Transactional.TxType.REQUIRED)
    public Iterable<T> findAllIterable(Sort var1) {
        return repository.findAll(var1);
    }

    @Transactional(value = Transactional.TxType.REQUIRED)
    public T getOne(ID var1) {
        return repository.getOne(var1);
    }

    @Transactional(value = Transactional.TxType.REQUIRED)
    public T save(T var1) {
        return repository.save(var1);
    }

    @Transactional(value = Transactional.TxType.REQUIRED)
    public Optional<T> findById(ID var1) {
        return repository.findById(var1);
    }

    @Transactional(value = Transactional.TxType.REQUIRED)
    public boolean existsById(ID var1) {
        return repository.existsById(var1);
    }

    @Transactional(value = Transactional.TxType.REQUIRED)
    public long count() {
        return repository.count();
    }

    @Transactional(value = Transactional.TxType.REQUIRED)
    public void deleteById(ID var1) {
        repository.deleteById(var1);
    }

    @Transactional(value = Transactional.TxType.REQUIRED)
    public void delete(T var1) {
        repository.delete(var1);
    }

    @Transactional
    public void deleteAllInBatch() {
        repository.deleteAllInBatch();
    }

    protected DAO getRepository() {
        return this.repository;
    }
}