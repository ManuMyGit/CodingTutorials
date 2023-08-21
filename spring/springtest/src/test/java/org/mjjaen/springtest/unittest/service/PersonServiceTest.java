package org.mjjaen.springtest.unittest.service;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mjjaen.springtest.businessObject.Person;
import org.mjjaen.springtest.repository.PersonRepository;
import org.mjjaen.springtest.service.impl.PersonServiceImpl;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PersonServiceTest {
    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PersonServiceImpl personService;

    private List<Person> personList;

    @BeforeEach
    public void setUp() {
        Person personOne = Person.builder().id(1).firstName("Aragorn").lastName("Elessar").birthDate(new GregorianCalendar(1950,  1, 1).getTime()).build();
        Person personTwo = Person.builder().id(2).firstName("Turin").lastName("Turambar").birthDate(new GregorianCalendar(1950,  1, 31).getTime()).build();
        Person personThree = Person.builder().id(3).firstName("Elu").lastName("Thingol").birthDate(new GregorianCalendar(1950,  6, 30).getTime()).build();
        Person personFour = Person.builder().id(4).firstName("Tar").lastName("Miniatur").birthDate(new GregorianCalendar(1950,  8, 30).getTime()).build();
        personList = Arrays.asList(personOne, personTwo, personThree, personFour);
    }


    @Test
    @DisplayName("This test validates whether the data.sql file executed is the testing one")
    @Order(1)
    public void shouldReturnFourElements() {
        when(personRepository.findAll()).thenReturn(personList);
        List<Person> personList = personService.findAll();
        Assertions.assertTrue(personList != null && personList.size() == 4);
    }

    @Test
    @DisplayName("This test validates get by name and like ignorecase methosd")
    @Order(2)
    public void shouldRetrieveOnlyOneRecord() {
        when(personRepository.findByFirstName("Aragorn")).thenReturn(Optional.of(personList.get(0)));
        when(personRepository.findByFirstNameContainingIgnoreCase("GoRn")).thenReturn(Optional.of(personList.get(0)));
        Optional<Person> person = personService.findByFirstName("Aragorn", false);
        Assertions.assertTrue(person.isPresent());
        Assertions.assertTrue(person.get().getFirstName().equals("Aragorn"));
        person = personService.findByFirstName("GoRn", true);
        Assertions.assertTrue(person.isPresent());
        Assertions.assertTrue(person.get().getFirstName().equals("Aragorn"));
    }

    @Test
    @DisplayName("This test validates empty optional returned by get by name")
    @Order(3)
    public void shouldNotRetrieveAnyRecord() {
        when(personRepository.findByFirstName("Elendil")).thenReturn(Optional.empty());
        Optional<Person> person = personService.findByFirstName("Elendil", false);
        Assertions.assertTrue(!person.isPresent());
    }
}
