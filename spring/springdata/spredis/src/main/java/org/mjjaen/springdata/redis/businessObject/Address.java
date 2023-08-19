package org.mjjaen.springdata.redis.businessObject;

import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Address implements Serializable {
    private static final long serialVersionUID = -7617254776111722682L;

    private String addressLine;
    private Integer number;
}
