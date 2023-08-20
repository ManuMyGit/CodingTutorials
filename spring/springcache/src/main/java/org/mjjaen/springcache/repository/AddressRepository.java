package org.mjjaen.springcache.repository;

import org.mjjaen.springcache.businessObject.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.util.List;

@Repository
public interface AddressRepository extends AddressRepositoryCustom, JpaRepository<Address, Long> {
    @Nullable
    List<Address> findByCountry(String country);
    @Nullable
    List<Address> findByStreet(String street);
    @Nullable
    Address findOneByStreet(String street);
    @Nullable
    List<Address> findByState(String state);
}
