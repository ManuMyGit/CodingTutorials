package org.mjjaen.springcache.businessObject;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Data
@Entity
@ToString
public class Payment extends BaseEntity<Long> {
    private static final long serialVersionUID = -7317250166111722682L;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST, optional = false)
    @JoinColumn(name = "funding_account", referencedColumnName = "id")
    private Account fundingAccount;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "payee_account", referencedColumnName = "id")
    private Account payeeAccount;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "payer_info", referencedColumnName = "id")
    private Person payerInfo;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "payee_info", referencedColumnName = "id")
    private Person payeeInfo;
    private Double amount;
}
