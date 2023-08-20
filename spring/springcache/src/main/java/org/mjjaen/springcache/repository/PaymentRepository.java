package org.mjjaen.springcache.repository;

import org.mjjaen.springcache.businessObject.Account;
import org.mjjaen.springcache.businessObject.Payment;
import org.mjjaen.springcache.businessObject.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.util.List;

@Repository
public interface PaymentRepository extends AddressRepositoryCustom, JpaRepository<Payment, Long> {
    @Nullable
    List<Payment> findByFundingAccount(Account fundingAccount);
    @Nullable
    List<Payment> findByPayeeAccount(Account payeeAccount);
    @Nullable
    List<Payment> findByPayerInfo(Person payerInfo);
    @Nullable
    List<Payment> findByPayeeInfo(Person payeeInfo);
    @Nullable
    List<Payment> findByAmount(Double amount);
}