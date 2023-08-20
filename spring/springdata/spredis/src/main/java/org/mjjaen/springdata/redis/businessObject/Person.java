package org.mjjaen.springdata.redis.businessObject;

import lombok.*;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@AllArgsConstructor
@Getter
@Builder
@ToString
@EqualsAndHashCode
@RedisHash("Person")
public class Person implements Serializable {
    private static final long serialVersionUID = -7117221176021721182L;

    private String id;
    private String name;
    private Double Salary;
    private Address address;
}
