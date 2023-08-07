package org.mjjaen.springdata.jpa.repository;

import org.mjjaen.springdata.jpa.businessObject.Address;
import org.mjjaen.springdata.jpa.businessObject.AddressDto;
import org.mjjaen.springdata.jpa.businessObject.AddressView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.Nullable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Repository
public interface AddressRepository extends AddressRepositoryCustom, JpaRepository<Address, Long> {
    List<Address> findByCountry(String country);
    @Nullable
    List<Address> findByStreet(@Nullable String street);
    @Async
    CompletableFuture<Address> findOneByStreet(String firstname);
    List<AddressDto> findByState(String state);
    <T> List<T> findByStateOrderByStreet(String state, Class<T> type);
    List<AddressView> findByStateOrderByCountry(String state);
}
