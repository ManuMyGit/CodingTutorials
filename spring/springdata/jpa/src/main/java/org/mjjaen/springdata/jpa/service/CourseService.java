package org.mjjaen.springdata.jpa.service;

import org.mjjaen.springdata.jpa.businessObject.Course;

import java.util.List;

public interface CourseService extends BaseService<Course, Long>{
    List<Course> findByTitleSentAsParameter(String title);
    List<Course> findByTitleSentAsParameterParamName(String title);
    List<Course> findByTitleSentAsParameterNativeQuery(String title);
    List<Course> findByTitleSentAsParameterParamNameNativeQuery(String title);
}
