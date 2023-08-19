package org.mjjaen.springdata.redis.repository.impl;

import org.mjjaen.springdata.redis.businessObject.Employee;
import org.mjjaen.springdata.redis.repository.EmployeeRepository;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Map;

@Repository
@Transactional
public class EmployeeRepositoryImpl implements EmployeeRepository {
    @Resource(name="employeeRedisTemplate")
    private HashOperations<String, String, Employee> hashOperations;

    private final String hashReference= "Employee";

    @Override
    public void save(Employee employee) {
        hashOperations.putIfAbsent(hashReference, employee.getId(), employee);
    }

    @Override
    public Employee get(String id) {
        return hashOperations.get(hashReference, id);
    }

    @Override
    public void update(Employee employee) {
        hashOperations.put(hashReference, employee.getId(), employee);
    }

    @Override
    public Map<String, Employee> getAll() {
        return hashOperations.entries(hashReference);
    }

    @Override
    public void delete(String id) {
        hashOperations.delete(hashReference, id);
    }
}
