package org.mjjaen.springdata.jpa.service.impl;

import org.mjjaen.springdata.jpa.businessObject.Course;
import org.mjjaen.springdata.jpa.repository.CourseRepository;
import org.mjjaen.springdata.jpa.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl extends BaseServiceImpl<Course, Long, CourseRepository> implements CourseService {
    @Autowired
    public CourseServiceImpl(CourseRepository repository) {
        super(repository);
    }

    @Override
    public List<Course> findByTitleSentAsParameter(String title) {
        return this.getRepository().findByTitleSentAsParameter(title);
    }

    @Override
    public List<Course> findByTitleSentAsParameterParamName(String title) {
        return this.getRepository().findByTitleSentAsParameterParamName(title);
    }

    @Override
    public List<Course> findByTitleSentAsParameterNativeQuery(String title) {
        return this.getRepository().findByTitleSentAsParameterNativeQuery(title);
    }

    @Override
    public List<Course> findByTitleSentAsParameterParamNameNativeQuery(String title) {
        return this.getRepository().findByTitleSentAsParameterParamNameNativeQuery(title);
    }
}
