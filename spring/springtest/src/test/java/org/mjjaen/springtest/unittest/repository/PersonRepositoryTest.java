package org.mjjaen.springtest.unittest.repository;

import org.junit.jupiter.api.*;
import org.mjjaen.springtest.businessObject.Person;
import org.mjjaen.springtest.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlGroup;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@TestPropertySource(locations = "classpath:application_test.properties")
@SqlGroup({
        @Sql(scripts = "classpath:test_schema.sql",
                config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED)),
        @Sql("classpath:test_data.sql")})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PersonRepositoryTest {
    @Autowired
    private PersonRepository personRepository;

    @Test
    @DisplayName("This test validates whether the data.sql file executed is the testing one")
    @Order(1)
    public void shouldReturnFourElements() {
        List<Person> personList = personRepository.findAll();
        Assertions.assertTrue(personList != null && personList.size() == 4);
    }

    @Test
    @DisplayName("This test validates get by name and like ignorecase methosd")
    @Order(2)
    public void shouldRetrieveOnlyOneRecord() {
        Optional<Person> person = personRepository.findByFirstName("Aragorn");
        Assertions.assertTrue(person.isPresent());
        Assertions.assertTrue(person.get().getFirstName().equals("Aragorn"));
        person = personRepository.findByFirstNameContainingIgnoreCase("GoRn");
        Assertions.assertTrue(person.isPresent());
        Assertions.assertTrue(person.get().getFirstName().equals("Aragorn"));
    }

    @Test
    @DisplayName("This test validates empty optional returned by get by name")
    @Order(3)
    public void shouldNotRetrieveAnyRecord() {
        Optional<Person> person = personRepository.findByFirstName("Elendil");
        Assertions.assertTrue(!person.isPresent());
    }
}
