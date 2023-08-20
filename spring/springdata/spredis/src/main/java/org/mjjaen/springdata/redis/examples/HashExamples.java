package org.mjjaen.springdata.redis.examples;

import lombok.extern.slf4j.Slf4j;
import org.mjjaen.springdata.redis.businessObject.Address;
import org.mjjaen.springdata.redis.businessObject.Customer;
import org.mjjaen.springdata.redis.businessObject.Employee;
import org.mjjaen.springdata.redis.businessObject.Person;
import org.mjjaen.springdata.redis.repository.CustomerRepository;
import org.mjjaen.springdata.redis.repository.EmployeeRepository;
import org.mjjaen.springdata.redis.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class HashExamples implements Examples {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PersonRepository personRepository;

    public HashExamples(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public void runExamples() {
        log.info("RUNNING HASH EXAMPLES ...");
        log.info("First approach to store objects: ClassName Id WholeJsonPayload ...");
        Employee employee = new Employee("1", "Thingol", 9.98, new Address("My address 1", 5));
        log.info("Saving " + employee + " to database ...");
        employeeRepository.save(employee);
        employee = new Employee("2", "Dior", 9.98, new Address("My address 2", 10));
        log.info("Saving " + employee + " to database ...");
        employeeRepository.save(employee);
        log.info("Getting employee with id = 2 ...");
        log.info(employeeRepository.get("2").toString());
        employee = new Employee("2", "Turgon", 9.98, new Address("My address 3", 15));
        log.info("Updating employee with id = 2 to " + employee);
        employeeRepository.update(employee);
        log.info("Getting employee with id = 2 ...");
        log.info(employeeRepository.get("2").toString());
        log.info("Getting all employees ...");
        employeeRepository.getAll().forEach((k, v) -> log.info(v.toString()));
        log.info("Deleting employee with id = 2 ...");
        employeeRepository.delete("2");
        log.info("Getting employee with id = 2 ...");
        log.info(employeeRepository.get("2") != null ? employeeRepository.get("2").toString() : "Employee not found");
        log.info("Deleting employee with id = 1 ...");
        employeeRepository.delete("1");
        log.info("Getting employee with id = 1 ...");
        log.info(employeeRepository.get("1") != null ? employeeRepository.get("1").toString() : "Employee not found");

        log.info("Second approach to store objects: ClassName:Id property value property value property value...");
        Customer customer = new Customer("1", "Thingol", 9.98, new Address("My address 1", 5));
        log.info("Saving " + customer + " to database ...");
        customerRepository.save(customer);
        customer = new Customer("2", "Dior", 9.98, new Address("My address 2", 10));
        log.info("Saving " + customer + " to database ...");
        customerRepository.save(customer);
        log.info("Getting customer with id = 2 ...");
        log.info(customerRepository.get("2").toString());
        customer =new Customer("2", "Turgon", 9.98, new Address("My address 3", 15));
        log.info("Updating customer with id = 2 to " + customer);
        customerRepository.update(customer);
        log.info("Getting customer with id = 2 ...");
        log.info(customerRepository.get("2").toString());
        log.info("Getting all customers ...");
        customerRepository.getAll().forEach((k, v) -> log.info(v.toString()));
        log.info("Deleting customer with id = 2 ...");
        customerRepository.delete("2");
        log.info("Getting customer with id = 2 ...");
        log.info(customerRepository.get("2") != null ? customerRepository.get("2").toString() : "Customer not found");
        log.info("Deleting customer with id = 1 ...");
        customerRepository.delete("1");
        log.info("Getting customer with id = 1 ...");
        log.info(customerRepository.get("1") != null ? customerRepository.get("1").toString() : "Customer not found");

        log.info("Third approach: Spring Data Repositories ...");
        Person person = new Person("1", "Thingol", 9.98, new Address("My address 1", 5));
        log.info("Saving " + person + " to database ...");
        personRepository.save(person);
        person = new Person("2", "Dior", 9.98, new Address("My address 2", 10));
        log.info("Saving " + person + " to database ...");
        personRepository.save(person);
        log.info("Getting person with id = 2 ...");
        log.info(personRepository.findById("2").toString());
        person = new Person("2", "Turgon", 9.98, new Address("My address 3", 15));
        log.info("Updating person with id = 2 to " + person);
        personRepository.save(person);
        log.info("Getting person with id = 2 ...");
        log.info(personRepository.findById("2").toString());
        log.info("Getting all persons ...");
        personRepository.findAll().forEach(e -> log.info(e.toString()));
        log.info("Deleting person with id = 2 ...");
        personRepository.deleteById("2");
        log.info("Getting person with id = 2 ...");
        log.info(personRepository.findById("2").isPresent() ? personRepository.findById("2").toString() : "Person not found");
        log.info("Deleting person with id = 1 ...");
        personRepository.deleteById("1");
        log.info("Getting person with id = 1 ...");
        log.info(personRepository.findById("1").isPresent() ? personRepository.findById("1").toString() : "Person not found");
    }
}
