package org.mjjaen.springcache.businessObject;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Data
@Entity
@ToString
public class Person extends BaseEntity<Long> {
    private static final long serialVersionUID = -7217254110111722682L;

    @Column(length = 50, name = "first_name")
    private String firstName;
    @Column(length = 50, name = "last_name")
    private String lastName;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST, optional = false)
    @JoinColumn(name = "address", referencedColumnName = "id")
    private Address address;
}
