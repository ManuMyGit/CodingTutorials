package org.mjjaen.springtest.unittest.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mjjaen.springtest.businessObject.Person;
import org.mjjaen.springtest.controller.PersonRestController;
import org.mjjaen.springtest.repository.PersonRepository;
import org.mjjaen.springtest.service.PersonService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PersonRestController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PersonControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonService personService;

    private List<Person> personList;

    @BeforeEach
    public void setUp(){
        Person personOne = Person.builder().id(1).firstName("Aragorn").lastName("Elessar").birthDate(new GregorianCalendar(1950,  1, 1).getTime()).build();
        Person personTwo = Person.builder().id(2).firstName("Turin").lastName("Turambar").birthDate(new GregorianCalendar(1950,  1, 31).getTime()).build();
        Person personThree = Person.builder().id(3).firstName("Elu").lastName("Thingol").birthDate(new GregorianCalendar(1950,  6, 30).getTime()).build();
        Person personFour = Person.builder().id(4).firstName("Tar").lastName("Miniatur").birthDate(new GregorianCalendar(1950,  8, 30).getTime()).build();
        personList = Arrays.asList(personOne, personTwo, personThree, personFour);
    }

    @Test
    @Order(1)
    public void shouldReturnListOfPerson() throws Exception {
        Mockito.when(personService.findAll()).thenReturn(personList);
        mockMvc.perform(get("/person"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(4)))
                .andExpect(jsonPath("$[0].firstName", Matchers.is("Aragorn")));
    }

    @Test
    @Order(2)
    public void shouldReturnOnePerson() throws Exception {
        Mockito.when(personService.findById(1)).thenReturn(Optional.of(personList.get(0)));
        mockMvc.perform(get("/person/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("firstName", Matchers.is("Aragorn")));
    }

    @Test
    @Order(3)
    public void shouldReturnNoutFoundException() throws Exception {
        Mockito.when(personService.findById(5)).thenReturn(Optional.empty());
        mockMvc.perform(get("/person/5"))
                .andExpect(status().isNotFound());
    }
}
