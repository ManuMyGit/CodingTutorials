package org.mjjaen.restapi;

import org.junit.jupiter.api.*;
import org.mjjaen.restapi.dto.TaskDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application_test.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RestApiApplicationTest {
    @Autowired
    private TestRestTemplate testRestTemplate;

    private static String taskId;

    @BeforeEach
    public void setup() {
        //testRestTemplate.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory());
    }

    @Test
    @Order(1)
    void shouldDeleteTaskCollection() {
        //Get all tasks
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON_VALUE);
        HttpEntity requestEntity = new HttpEntity<>(null, requestHeaders);
        ResponseEntity<List<TaskDto>> responseGet = testRestTemplate.exchange("/v1/task", HttpMethod.GET, requestEntity, new ParameterizedTypeReference<>() {});
        assertTrue(responseGet.getStatusCode().equals(HttpStatus.OK));
        List<TaskDto> taskDtoList = responseGet.getBody();
        assertTrue(taskDtoList != null && taskDtoList.size() >= 0);

        taskDtoList.forEach(task -> {
            ResponseEntity<Void> responseDelete = testRestTemplate.exchange("/v1/task/" + task.getId(), HttpMethod.DELETE, HttpEntity.EMPTY, Void.class);
            assertTrue(responseDelete.getStatusCode().equals(HttpStatus.NO_CONTENT));
        });
    }

    @Test
    @Order(2)
    void shouldCreateNewTask() {
        TaskDto taskDto = TaskDto.builder().description("Task test").priority("MEDIUM").definitionOfDone("Any will work").dataOne(10).dataTwo(20).dataThree(30).dataFour(40).build();

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON_VALUE);
        HttpEntity requestEntity = new HttpEntity<>(taskDto, requestHeaders);

        ResponseEntity<TaskDto> response = testRestTemplate.exchange("/v1/task", HttpMethod.POST, requestEntity, new ParameterizedTypeReference<>() {});
        assertTrue(response.getStatusCode().equals(HttpStatus.CREATED));

        TaskDto taskDtoResponse = response.getBody();
        assertTrue(!taskDtoResponse.getId().equals(""));
        assertTrue(taskDtoResponse.getDateCreated() != null && taskDtoResponse.getDateCreated().isBefore(LocalDateTime.now()));
        assertTrue(taskDtoResponse.getLastUpdated() != null && taskDtoResponse.getLastUpdated().isBefore(LocalDateTime.now()));
        taskId = taskDtoResponse.getId();
    }

    @Test
    @Order(3)
    void shouldGetAnExistingTask() {
        ResponseEntity<TaskDto> response = testRestTemplate.exchange("/v1/task/" + taskId, HttpMethod.GET, HttpEntity.EMPTY, new ParameterizedTypeReference<>() {});
        assertTrue(response.getStatusCode().equals(HttpStatus.OK));

        TaskDto taskDtoResponse = response.getBody();
        assertTrue(taskDtoResponse.getId().equals(taskId));
    }

    @Test
    @Order(4)
    void shouldUpdateAnExistingTask() {
        //Get the entity
        ResponseEntity<TaskDto> response = testRestTemplate.exchange("/v1/task/" + taskId, HttpMethod.GET, HttpEntity.EMPTY, new ParameterizedTypeReference<>() {});
        assertTrue(response.getStatusCode().equals(HttpStatus.OK));

        TaskDto taskDtoResponse = response.getBody();
        assertTrue(taskDtoResponse.getId().equals(taskId));

        //Update the entity
        taskDtoResponse.setDescription("New description");
        taskDtoResponse.setPriority("LOW");
        taskDtoResponse.setDefinitionOfDone("New DoD");
        taskDtoResponse.setDataOne(11);
        taskDtoResponse.setDataTwo(21);
        taskDtoResponse.setDataThree(31);
        taskDtoResponse.setDataFour(41);

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON_VALUE);
        HttpEntity requestEntity = new HttpEntity<>(taskDtoResponse, requestHeaders);

        response = testRestTemplate.exchange("/v1/task/" + taskId, HttpMethod.PATCH, requestEntity, new ParameterizedTypeReference<>() {});
        assertTrue(response.getStatusCode().equals(HttpStatus.ACCEPTED));

        //Verify the returned results are the same as the sent ones
        TaskDto taskDtoResponse2 = response.getBody();
        assertTrue(taskDtoResponse.getId().equals(taskDtoResponse2.getId()));
        assertTrue(taskDtoResponse.getDescription().equals(taskDtoResponse2.getDescription()));
        assertTrue(taskDtoResponse.getPriority().equals(taskDtoResponse2.getPriority()));
        assertTrue(taskDtoResponse.getDefinitionOfDone().equals(taskDtoResponse2.getDefinitionOfDone()));
        assertTrue(taskDtoResponse.getDataOne().equals(taskDtoResponse2.getDataOne()));
        assertTrue(taskDtoResponse.getDataTwo().equals(taskDtoResponse2.getDataTwo()));
        assertTrue(taskDtoResponse.getDataThree().equals(taskDtoResponse2.getDataThree()));
        assertTrue(taskDtoResponse.getDataFour().equals(taskDtoResponse2.getDataFour()));

        //Verify the data stored in the database is the same as the returned one
        response = testRestTemplate.exchange("/v1/task/" + taskId, HttpMethod.GET, HttpEntity.EMPTY, new ParameterizedTypeReference<>() {});
        assertTrue(response.getStatusCode().equals(HttpStatus.OK));
        taskDtoResponse2 = response.getBody();
        assertTrue(taskDtoResponse.getId().equals(taskDtoResponse2.getId()));
        assertTrue(taskDtoResponse.getDescription().equals(taskDtoResponse2.getDescription()));
        assertTrue(taskDtoResponse.getPriority().equals(taskDtoResponse2.getPriority()));
        assertTrue(taskDtoResponse.getDefinitionOfDone().equals(taskDtoResponse2.getDefinitionOfDone()));
        assertTrue(taskDtoResponse.getDataOne().equals(taskDtoResponse2.getDataOne()));
        assertTrue(taskDtoResponse.getDataTwo().equals(taskDtoResponse2.getDataTwo()));
        assertTrue(taskDtoResponse.getDataThree().equals(taskDtoResponse2.getDataThree()));
        assertTrue(taskDtoResponse.getDataFour().equals(taskDtoResponse2.getDataFour()));
    }

    @Test
    @Order(5)
    void shouldPartiallyUpdateAnExistingTask() {
        //Get the entity
        ResponseEntity<TaskDto> response = testRestTemplate.exchange("/v1/task/" + taskId, HttpMethod.GET, HttpEntity.EMPTY, new ParameterizedTypeReference<>() {});
        assertTrue(response.getStatusCode().equals(HttpStatus.OK));

        TaskDto taskDtoResponse = response.getBody();
        assertTrue(taskDtoResponse.getId().equals(taskId));

        //Update the entity
        taskDtoResponse.setDescription("My new description");

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON_VALUE);
        HttpEntity requestEntity = new HttpEntity<>(taskDtoResponse, requestHeaders);

        response = testRestTemplate.exchange("/v1/task/" + taskId, HttpMethod.PATCH, requestEntity, new ParameterizedTypeReference<>() {});
        assertTrue(response.getStatusCode().equals(HttpStatus.ACCEPTED));

        //Verify the returned results are the same as the sent ones
        TaskDto taskDtoResponse2 = response.getBody();
        assertTrue(taskDtoResponse.getId().equals(taskDtoResponse2.getId()));
        assertTrue(taskDtoResponse.getDescription().equals(taskDtoResponse2.getDescription()));
        assertTrue(taskDtoResponse.getPriority().equals(taskDtoResponse2.getPriority()));
        assertTrue(taskDtoResponse.getDefinitionOfDone().equals(taskDtoResponse2.getDefinitionOfDone()));
        assertTrue(taskDtoResponse.getDataOne().equals(taskDtoResponse2.getDataOne()));
        assertTrue(taskDtoResponse.getDataTwo().equals(taskDtoResponse2.getDataTwo()));
        assertTrue(taskDtoResponse.getDataThree().equals(taskDtoResponse2.getDataThree()));
        assertTrue(taskDtoResponse.getDataFour().equals(taskDtoResponse2.getDataFour()));

        //Verify the data stored in the database is the same as the returned one
        response = testRestTemplate.exchange("/v1/task/" + taskId, HttpMethod.GET, HttpEntity.EMPTY, new ParameterizedTypeReference<>() {});
        assertTrue(response.getStatusCode().equals(HttpStatus.OK));
        taskDtoResponse2 = response.getBody();
        assertTrue(taskDtoResponse.getId().equals(taskDtoResponse2.getId()));
        assertTrue(taskDtoResponse.getDescription().equals(taskDtoResponse2.getDescription()));
        assertTrue(taskDtoResponse.getPriority().equals(taskDtoResponse2.getPriority()));
        assertTrue(taskDtoResponse.getDefinitionOfDone().equals(taskDtoResponse2.getDefinitionOfDone()));
        assertTrue(taskDtoResponse.getDataOne().equals(taskDtoResponse2.getDataOne()));
        assertTrue(taskDtoResponse.getDataTwo().equals(taskDtoResponse2.getDataTwo()));
        assertTrue(taskDtoResponse.getDataThree().equals(taskDtoResponse2.getDataThree()));
        assertTrue(taskDtoResponse.getDataFour().equals(taskDtoResponse2.getDataFour()));
    }

    @Test
    @Order(6)
    void shouldGetANonExistingTask() {
        ResponseEntity<TaskDto> response = testRestTemplate.exchange("/v1/task/11f111f11a1ad11111efa1e1", HttpMethod.GET, HttpEntity.EMPTY, new ParameterizedTypeReference<>() {});
        assertTrue(response.getStatusCode().equals(HttpStatus.NOT_FOUND));
    }

    @Test
    @Order(7)
    void shouldUpdateANonExistingTask() {
        //Get the entity
        ResponseEntity<TaskDto> response = testRestTemplate.exchange("/v1/task/" + taskId, HttpMethod.GET, HttpEntity.EMPTY, new ParameterizedTypeReference<>() {});
        assertTrue(response.getStatusCode().equals(HttpStatus.OK));

        TaskDto taskDtoResponse = response.getBody();
        assertTrue(taskDtoResponse.getId().equals(taskId));

        //Update the entity
        taskDtoResponse.setDescription("New description");
        taskDtoResponse.setPriority("LOW");
        taskDtoResponse.setDefinitionOfDone("New DoD");

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON_VALUE);
        HttpEntity requestEntity = new HttpEntity<>(taskDtoResponse, requestHeaders);

        response = testRestTemplate.exchange("/v1/task/11f111f11a1ad11111efa1e1", HttpMethod.PATCH, requestEntity, new ParameterizedTypeReference<>() {});
        assertTrue(response.getStatusCode().equals(HttpStatus.NOT_FOUND));
    }

    @Test
    @Order(8)
    void shouldDeleteANonExistingTask() {
        ResponseEntity<Void> responseDelete = testRestTemplate.exchange("/v1/task/11f111f11a1ad11111efa1e1", HttpMethod.DELETE, HttpEntity.EMPTY, Void.class);
        assertTrue(responseDelete.getStatusCode().equals(HttpStatus.NOT_FOUND));
    }

    @Test
    @Order(9)
    void shouldCreateATaskWithInvalidValues() {
        TaskDto taskDto = TaskDto.builder().description("Task test").priority("INVALID").definitionOfDone("Any will work").build();

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON_VALUE);
        HttpEntity requestEntity = new HttpEntity<>(taskDto, requestHeaders);

        ResponseEntity<TaskDto> response = testRestTemplate.exchange("/v1/task", HttpMethod.POST, requestEntity, new ParameterizedTypeReference<>() {});
        assertTrue(response.getStatusCode().equals(HttpStatus.BAD_REQUEST));
    }
}
