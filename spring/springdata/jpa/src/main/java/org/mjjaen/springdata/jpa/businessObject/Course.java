package org.mjjaen.springdata.jpa.businessObject;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Course extends BaseEntity<Long> {
    @Column(length = 100)
    private String title;
    @ManyToOne(targetEntity=Teacher.class, fetch = FetchType.LAZY)
    @JoinColumn(name="teacher")
    private Teacher teacher;
    @ManyToMany(mappedBy="courses")
    private List<Student> students;

    @Override
    public String toString() {
        return "{id: ".concat(id.toString()).concat(", title: ").concat(title).concat("}");
    }
}