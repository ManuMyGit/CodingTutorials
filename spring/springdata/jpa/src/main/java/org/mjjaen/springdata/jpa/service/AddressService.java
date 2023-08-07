package org.mjjaen.springdata.jpa.service;

import org.mjjaen.springdata.jpa.businessObject.Address;
import org.mjjaen.springdata.jpa.businessObject.AddressDto;
import org.mjjaen.springdata.jpa.businessObject.AddressView;
import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface AddressService extends BaseService<Address, Long> {
    List<Address> findByCountry(String country);
    List<Address> findByStreetNullMethodAndParameter(String street);
    @Async
    CompletableFuture<Address> findOneByStreet(String firstname);
    List<AddressDto> findByState(String state);
    List<AddressDto> findByStateOrderByStreet(String state);
    List<AddressView> findByStateOrderByCountry(String state);
}
