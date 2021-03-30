package org.mjjaen.springdata.jpa.businessObject;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Address extends BaseEntity<Long> {
    @Column(length = 100)
    private String street;
    @Column(length = 50)
    private String city;
    @Column(length = 2)
    private String state;
    @Column(length = 5)
    private String zipCode;
    @Column(length = 15)
    private String country;

    @Override
    public String toString() {
        return "{id: ".concat(id.toString()).concat(", street: ").concat(street).concat(", city: ").concat(city).concat(", state: ").concat(state).concat(", zipCode: ").concat(zipCode).concat(", country: ").concat(country).concat("}");
    }
}
