package org.mjjaen.springdata.jpa.businessObject;

import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Data
@NamedQueries({
        @NamedQuery(name = "Student.findByGender", query = "FROM Student s WHERE s.gender = ?1 ORDER BY s.firstName ASC")
})
public class Student extends BaseEntity<Long> {
    @Column(length = 100)
    private String firstName;
    @Column(length = 100)
    private String lastName;
    @Enumerated(EnumType.STRING)
    @Column(length = 6)
    private Gender gender;
    @Column
    private Integer age;
    //One-to-One relationship
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "address")
    private Address address;
    //Many-to-Many relationship
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name="course_student", joinColumns=@JoinColumn(name="student"), inverseJoinColumns=@JoinColumn(name="course"))
    private List<Course> courses;

    @Override
    public String toString() {
        String s = courses != null ? courses.stream().map(Object::toString).collect(Collectors.joining(", ")) : "";
        return "{id: ".concat(id.toString()).concat(", firstName: ").concat(firstName).concat(", lastName: ").concat(lastName).concat(", gender: "). concat(gender.toString()).concat(", address: ").concat(address.toString()).concat(", courses: ").concat(s).concat("}");
    }
}
