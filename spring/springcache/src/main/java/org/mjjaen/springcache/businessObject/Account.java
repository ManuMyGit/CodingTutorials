package org.mjjaen.springcache.businessObject;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;

@Data
@Entity
@ToString
public class Account extends BaseEntity<Long> {
    private static final long serialVersionUID = -7517123776111722682L;

    @Column(length = 9, name = "routing_number")
    private String routingNumber;
    @Column(length = 17, name = "account_number")
    private String accountNumber;
}
