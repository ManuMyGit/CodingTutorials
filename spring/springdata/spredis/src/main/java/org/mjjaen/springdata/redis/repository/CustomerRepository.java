package org.mjjaen.springdata.redis.repository;

import org.mjjaen.springdata.redis.businessObject.Customer;

import java.util.Map;

public interface CustomerRepository {
    void save(Customer employee);
    Customer get(String id);
    void update(Customer employee);
    Map<String, Customer> getAll();
    void delete(String id);
}
