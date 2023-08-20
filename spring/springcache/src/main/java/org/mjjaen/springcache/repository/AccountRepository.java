package org.mjjaen.springcache.repository;

import org.mjjaen.springcache.businessObject.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.util.List;

@Repository
public interface AccountRepository extends AddressRepositoryCustom, JpaRepository<Account, Long> {
    @Nullable
    List<Account> findByRoutingNumber(String routingNumber);
    @Nullable
    List<Account> findByAccountNumber(String accountNumber);
    @Nullable
    Account findByRoutingNumberAndAccountNumber(String routingNumber, String accountNumber);
}
