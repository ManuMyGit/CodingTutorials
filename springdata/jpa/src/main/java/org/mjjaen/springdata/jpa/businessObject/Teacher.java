package org.mjjaen.springdata.jpa.businessObject;

import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Entity
public class Teacher extends BaseEntity<Long> {
    @Column(length = 100)
    private String firstName;
    @Column(length = 100)
    private String lastName;
    @OneToMany(targetEntity=Course.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "teacher")
    List<Course> courses;

    @Override
    public String toString() {
        String s = courses != null ? courses.stream().map(Object::toString).collect(Collectors.joining(", ")) : "";
        return "{id: ".concat(id.toString()).concat(", firstName: ").concat(firstName).concat(", lastName: ").concat(lastName).concat(", courses: ").concat(s).concat("}");
    }
}
