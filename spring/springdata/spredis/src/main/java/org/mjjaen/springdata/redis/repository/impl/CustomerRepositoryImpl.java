package org.mjjaen.springdata.redis.repository.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.mjjaen.springdata.redis.businessObject.Address;
import org.mjjaen.springdata.redis.businessObject.Customer;
import org.mjjaen.springdata.redis.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Repository
@Transactional
@Slf4j
public class CustomerRepositoryImpl implements CustomerRepository {
    @Resource(name="customerRedisTemplate")
    private HashOperations<String, String, Object> hashOperations;

    @Autowired
    private RedisTemplate stringRedisTemplate;

    @Override
    public void save(Customer customer) {
        hashOperations.putIfAbsent("customer:".concat(customer.getId()), "name", customer.getName());
        hashOperations.putIfAbsent("customer:".concat(customer.getId()), "salary", customer.getSalary());
        hashOperations.putIfAbsent("customer:".concat(customer.getId()), "address", customer.getAddress());
    }

    @Override
    public Customer get(String id) {
        Map<String, Object> entries;
        if((entries = hashOperations.entries("customer:".concat(id))) == null || entries != null && entries.size() == 0)
            return null;
        Customer customer = new Customer();
        customer.setId(id);
        customer.setName((String)entries.get("name"));
        customer.setSalary((Double)entries.get("salary"));
        ObjectMapper mapper = new ObjectMapper();
        Address address = mapper.convertValue(entries.get("address"), Address.class);
        customer.setAddress(address);
        return customer;
    }

    @Override
    public void update(Customer customer) {
        hashOperations.put("customer:".concat(customer.getId()), "name", customer.getName());
        hashOperations.put("customer:".concat(customer.getId()), "salary", customer.getSalary());
        hashOperations.put("customer:".concat(customer.getId()), "address", customer.getAddress());
    }

    @Override
    public Map<String, Customer> getAll() {
        Map<String, Customer> customers = new HashMap();
        Set<String> keys = stringRedisTemplate.keys("customer:*");
        keys.forEach(e -> {
            Customer customer = get(e.substring(e.indexOf(":") + 1));
            customers.put(e, customer);
        });
        return customers;
    }

    @Override
    public void delete(String id) {
        hashOperations.delete("customer:".concat(id), "name", "salary", "address");
    }
}
