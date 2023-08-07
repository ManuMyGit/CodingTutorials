package org.mjjaen.springdata.jpa.service.impl;

import org.mjjaen.springdata.jpa.businessObject.Address;
import org.mjjaen.springdata.jpa.businessObject.AddressDto;
import org.mjjaen.springdata.jpa.businessObject.AddressView;
import org.mjjaen.springdata.jpa.repository.AddressRepository;
import org.mjjaen.springdata.jpa.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class AddressServiceImpl extends BaseServiceImpl<Address, Long, AddressRepository> implements AddressService {
    @Autowired
    public AddressServiceImpl(AddressRepository repository) {
        super(repository);
    }

    @Transactional(value = Transactional.TxType.REQUIRED)
    public List<Address> findByCountry(String country) {
        return this.getRepository().findByCountry(country);
    }

    @Transactional(value = Transactional.TxType.REQUIRED)
    public List<Address> findByStreetNullMethodAndParameter(String street) {
        return this.getRepository().findByStreet(street);
    }

    @Transactional(value = Transactional.TxType.REQUIRED)
    public CompletableFuture<Address> findOneByStreet(String firstname) {
        return this.getRepository().findOneByStreet(firstname);
    }

    @Transactional(value = Transactional.TxType.REQUIRED)
    public List<AddressDto> findByState(String state) {
        return this.getRepository().findByState(state);
    }

    @Transactional(value = Transactional.TxType.REQUIRED)
    public List<AddressDto> findByStateOrderByStreet(String state) {
        return this.getRepository().findByStateOrderByStreet(state, AddressDto.class);
    }

    @Transactional(value = Transactional.TxType.REQUIRED)
    public List<AddressView> findByStateOrderByCountry(String state) {
        return this.getRepository().findByStateOrderByCountry(state);
    }
}
