package org.mjjaen.springtest.integrationtest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mjjaen.springtest.businessObject.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlGroup;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application_test.properties")
@SqlGroup({
        @Sql(scripts = "classpath:test_schema.sql",
                config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED)),
        @Sql("classpath:test_data.sql")})
public class PersonControllerTest2 {
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    void shouldCreateNewCustomers() {

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON_VALUE);

        HttpEntity requestEntity = new HttpEntity<>(null, requestHeaders);

        ResponseEntity<List<Person>> response = testRestTemplate.exchange("/person",
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<List<Person>>() {
                });

        List<Person> personList = response.getBody();
        Assertions.assertTrue(personList != null && personList.size() == 4);
    }
}
