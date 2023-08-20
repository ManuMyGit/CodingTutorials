package org.mjjaen.springcache.businessObject;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;

@Data
@Entity
@ToString
public class Address extends BaseEntity<Long> {
    private static final long serialVersionUID = -7417254776111593682L;

    @Column(length = 100)
    private String street;
    @Column(length = 50)
    private String city;
    @Column(length = 2)
    private String state;
    @Column(length = 5, name = "zip_code")
    private String zipCode;
    @Column(length = 2)
    private String country;
}
