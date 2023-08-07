package org.mjjaen.springdata.jpa.service.impl;

import org.mjjaen.springdata.jpa.businessObject.Teacher;
import org.mjjaen.springdata.jpa.repository.TeacherRepository;
import org.mjjaen.springdata.jpa.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class TeacherServiceImpl extends BaseServiceImpl<Teacher, Long, TeacherRepository> implements TeacherService {
    @Autowired
    public TeacherServiceImpl(TeacherRepository repository) {
        super(repository);
    }

    @Transactional(value = Transactional.TxType.REQUIRED)
    public Optional<Teacher> findOptionalByFirstName(String firstName) {
        return this.getRepository().findOptionalByFirstName(firstName);
    }

    @Transactional(value = Transactional.TxType.REQUIRED)
    public Page<Teacher> findByFirstNameLike(String firstName, Pageable pageable) {
        return this.getRepository().findByFirstNameLike(firstName, pageable);
    }

    @Transactional(value = Transactional.TxType.REQUIRED)
    public List<Teacher> findByFirstNameIn(Collection<String> firstNames, Sort sort) {
        return this.getRepository().findByFirstNameIn(firstNames, sort);
    }

    @Transactional(value = Transactional.TxType.REQUIRED)
    public List<Teacher> findTop3ByLastNameLike(String lastName) {
        return this.getRepository().findTop3ByLastNameLike(lastName);
    }
}
