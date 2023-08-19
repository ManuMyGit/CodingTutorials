package org.mjjaen.springdata.redis.businessObject;

import lombok.*;

import java.io.Serializable;


@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Customer implements Serializable {
    private static final long serialVersionUID = -7717324738021428682L;
    
    private String id;
    private String name;
    private Double Salary;
    private Address address;
}
