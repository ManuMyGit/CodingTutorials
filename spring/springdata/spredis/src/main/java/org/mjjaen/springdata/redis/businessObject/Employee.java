package org.mjjaen.springdata.redis.businessObject;

import java.io.Serializable;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Employee implements Serializable {
    private static final long serialVersionUID = -7817224776021728682L;

    private String id;
    private String name;
    private Double Salary;
    private Address address;
}