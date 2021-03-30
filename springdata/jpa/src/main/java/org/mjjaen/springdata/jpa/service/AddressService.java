package org.mjjaen.springdata.jpa.service;

import org.mjjaen.springdata.jpa.businessObject.Address;
import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface AddressService extends BaseService<Address, Long> {
    List<Address> findByCountry(String country);
    List<Address> findByStreetNullMethodAndParameter(String street);
    @Async
    CompletableFuture<Address> findOneByStreet(String firstname);
}
