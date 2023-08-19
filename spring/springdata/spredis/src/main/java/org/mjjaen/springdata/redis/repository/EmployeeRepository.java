package org.mjjaen.springdata.redis.repository;

import org.mjjaen.springdata.redis.businessObject.Employee;

import java.util.Map;

public interface EmployeeRepository {
    void save(Employee employee);
    Employee get(String id);
    void update(Employee employee);
    Map<String, Employee> getAll();
    void delete(String id);
}
