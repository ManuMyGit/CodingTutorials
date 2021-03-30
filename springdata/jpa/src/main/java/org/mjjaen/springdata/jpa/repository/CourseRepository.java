package org.mjjaen.springdata.jpa.repository;

import org.mjjaen.springdata.jpa.businessObject.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends CourseRepositoryCustom, JpaRepository<Course, Long> {
    @Query("select c from Course c where c.title = ?1")
    List<Course> findByTitleSentAsParameter(String tittle);
    @Query("select c from Course c where c.title = :title")
    List<Course> findByTitleSentAsParameterParamName(@Param("title") String tittle);
    @Query(value = "select * from course where title = ?1", nativeQuery = true)
    List<Course> findByTitleSentAsParameterNativeQuery(String tittle);
    @Query(value = "select * from course where title = :title", nativeQuery = true)
    List<Course> findByTitleSentAsParameterParamNameNativeQuery(@Param("title") String tittle);
}
