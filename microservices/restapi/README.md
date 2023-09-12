# INTRODUCTION
This project is a complete example about how to implement a REST API by using Spring (Spring Boot).

The features that can be found in this project are:
- Rest Controller.
  - Methods GET, POST, PATCH and DELETE exposed for CRUD operations.
  - Paging, filtering and sorting API capabilities.
  - Method GET exposed to call another API.
- Documentation (Open API / Swagger).
- Request management:
  - Header management.
  - Path variable management.
  - Request parameter management.
- Response management:
  - Empty and real body.
  - Response type.
  - Header manipulation.
- Error management.
  - Global error management.
  - Exception specific error management.
- Asynchronous operations.
  - Using Spring annotations.
  - Using CompletableFuture.
- Database
  - Spring Data MongoDB
  - Entity auditing capabilities.
  - Spring Data queries.
  - Custom queries using MongoTemplate.
- DTO layer.
- Logging
- Controller / Service / Repository layers separation.
- Integrated testing.

# CONTROLLER
## [Get](https://github.com/ManuMyGit/CodingTutorials/blob/main/microservices/restapi/src/main/java/org/mjjaen/restapi/controller/TaskController.java)
Two operations defined:
- Get by id
- Get all

### Get by id
- Endpoint declared with `@GetMapping`.
- Endpoint returning JSON (`produces = MediaType.APPLICATION_JSON_VALUE`) body (`@ResponseBody`). 
- Id is retrieved by using `@PathVariable`.
- Async operation based on `CompletableFuture` (in the controller) + `@Async` in the service and persistence layers.
- Request header retrieved by using `@RequestHeader`.
- Response header manipulated by using the parameter `HttpServletResponse`.
- Summary added to the documentation by using `@Operation`.
- Controller calls service method to get the task based on the Id.
- Transform entity into DTO to return the response.
- Controller returns the DTO (HTTP 200).

### Get all
- Endpoint declared with `@GetMapping`.
- Endpoint returning JSON (`produces = MediaType.APPLICATION_JSON_VALUE`) body (`@ResponseBody`).
- A paginated response is returned.
- Filter params are retrieved by using `@RequestParam`.
- `Pageable` used for pagination and ordering.
- Async operation based on Spring `@Async` in the controller, service and persistence layers.
- Request header retrieved by using `@RequestHeader`.
- Response header manipulated by using the parameter `HttpServletResponse`.
- Summary added to the documentation by using `@Operation`.
- Controller calls service method to get the list of tasks based on filters and paging.
- Transform list (page) of entities into list (page) of DTOs to return the response.
- Controller returns the list of DTOs (HTTP 200).

### [Get location](https://github.com/ManuMyGit/CodingTutorials/blob/main/microservices/restapi/src/main/java/org/mjjaen/restapi/controller/CallingOtherServiceController.java)
- Endpoint declared with `@GetMapping`.
- Endpoint returning JSON (`produces = MediaType.APPLICATION_JSON_VALUE`) body (`@ResponseBody`).
- Async operation based on Completable future.
- External API call with RestTemplate.

## [Post](https://github.com/ManuMyGit/CodingTutorials/blob/main/microservices/restapi/src/main/java/org/mjjaen/restapi/controller/TaskController.java)
- Endpoint declared with `@PostMapping`.
- Endpoint retrieving JSON (`consumes = MediaType.APPLICATION_JSON_VALUE`) object from the body (`@RequestBody`).
- Endpoint returning JSON (`produces = MediaType.APPLICATION_JSON_VALUE`) body (`@ResponseBody`).
- Request object is validated by using `@Valid`. At this moment, it is Spring MVC the one validating the data, not the persistence layer. Exception MethodArgumentNotValidException is thrown in case of failure.
- Async operation based on Spring `@Async` in the controller, service and persistence layers.
- Request header retrieved by using `@RequestHeader`.
- Response header manipulated by using the parameter `HttpServletResponse`.
- Summary added to the documentation by using `@Operation`.
- Controller calls service method to get the task created.
- DTO is transformed in entity before calling the service to get the task created in the DB.
- Stored entity is transformed into DTO before sending the response back.
- Controller returns the object created (HTTP 201).

## [Patch](https://github.com/ManuMyGit/CodingTutorials/blob/main/microservices/restapi/src/main/java/org/mjjaen/restapi/controller/TaskController.java)
- Endpoint declared with `@PatchMapping`.
- Endpoint retrieving JSON (`consumes = MediaType.APPLICATION_JSON_VALUE`) object from the body (`@RequestBody`).
- Endpoint returning JSON (`produces = MediaType.APPLICATION_JSON_VALUE`) body (`@ResponseBody`).
- Id is retrieved by using `@PathVariable`.
- Request object is not validated as the patch allows to update individual fields (which might be required at entity level).
- Async operation based on Spring `@Async` in the controller, service and persistence layers.
- Request header retrieved by using `@RequestHeader`.
- Response header manipulated by using the parameter `HttpServletResponse`.
- Summary added to the documentation by using `@Operation`.
- Controller calls service method to get the task updated.
- DTO is transformed in entity before calling the service to get the task updated.
- Updated entity is transformed into DTO before sending the response back.
- Controller returns the object updated (HTTP 202).

## [Delete](https://github.com/ManuMyGit/CodingTutorials/blob/main/microservices/restapi/src/main/java/org/mjjaen/restapi/controller/TaskController.java)
- Endpoint declared with `@DeleteMapping`.
- Id is retrieved by using `@PathVariable`.
- Async operation based on Spring `@Async` in the controller, service and persistence layers.
- Request header retrieved by using `@RequestHeader`.
- Response header manipulated by using the parameter `HttpServletResponse`.
- Summary added to the documentation by using `@Operation`.
- Controller calls service method to get the task deleted.
- Controller returns an empty response (HTTP 204).

# SERVICE
## [Interface](https://github.com/ManuMyGit/CodingTutorials/blob/main/microservices/restapi/src/main/java/org/mjjaen/restapi/service/TaskService.java)
The interface declares 5 methods to support the basic CRUD operations:
- `CompletableFuture<Task> findById(ObjectId id);`
- `CompletableFuture<Page<Task>> findAll(final String search, final Pageable pageable);`
- `CompletableFuture<Task> insert(@Valid Task task);`
- `CompletableFuture<Task> update(Task task, ObjectId id);`
- `CompletableFuture<Void> deleteById(ObjectId id);`

## [Implementation](https://github.com/ManuMyGit/CodingTutorials/blob/main/microservices/restapi/src/main/java/org/mjjaen/restapi/service/impl/TaskServiceImpl.java)
The implementation declares the body of the 5 methods from the interface. Additionally, it makes use of an instance of TaskRepository for database layer operations. There are 3 type of approaches:
- findById, update, deleteById:
  - First it gets the task from the database. If it does not exist, it throws a DataNotFoundException exception. If it does, it performs the operation:
    - In case of get, it returns the data.
    - In case of update, it inspects the properties from the incoming object, update the retrieved object with those properties and updates the data in the DB.
    - In case of delete, it removes the data.
- findAll
  - It simply uses the repository object to perform the operation.
- insert
  - Before performing the operation, it removes a few fields from the object in case they were part of the request.

# REPOSITORY
## Interface
Two interfaces are declared here:
- TaskRepository
- TaskRepositoryCustom

### [TaskRepository](https://github.com/ManuMyGit/CodingTutorials/blob/main/microservices/restapi/src/main/java/org/mjjaen/restapi/repository/TaskRepository.java)
This interface extends from MongoRepository, hence inheriting all methods available + methods build by using naming convention. One method is declared to return a CompletableFuture object. There is no need to declare save, update or delete methods are they are provided by the MongoRepository interface (directly or indirectly).

### [TaskRepositoryCustom](https://github.com/ManuMyGit/CodingTutorials/blob/main/microservices/restapi/src/main/java/org/mjjaen/restapi/repository/TaskRepositoryCustom.java)
This interface is used to declare custom methods. One method is declared to make use of filtering + paging.

### [Implementation](https://github.com/ManuMyGit/CodingTutorials/blob/main/microservices/restapi/src/main/java/org/mjjaen/restapi/repository/impl/TaskRepositoryImpl.java)
This class implements the method from the TaskRepositoryCustom interface. It converts the String search parameter into a Query object to be used with the MongoTemplate in addition to the Pageable object (for paging + sorting).

### Example of full query API request
This is an example with filtering + pagination + sorting:
```shell
[HOST:PORT]/v1/task?page=0&size=10&sort=id,desc&sort=description,desc&priority=LOW&dataOne>=1
```

# ENTITY
## [Task](https://github.com/ManuMyGit/CodingTutorials/blob/main/microservices/restapi/src/main/java/org/mjjaen/restapi/model/Task.java)
The entity class declares the data modeling. It makes use of Lombok, so no methods are implemented.
- `@Document`: Spring Data annotation to declare the class as a MongoDB document for persistance.
- `@Data`: Lombok annotation. It is a shortcut to `@ToString`, `@Getter`, `@Setter`, `@EqualsAndHashCode` and `@RequiredArgConstructor`.
- `@AllArgsConstructor`: Lombok annotation that declares a constructor with all fields.
- `@NoArgsConstructor`: Lombok annotation that declares a constructor with no fields.
- `@Builder`: Lombok annotation that creates a Builder for the class.

Additionally, it uses:
- `@Id` Spring Data annotation to declare the Id of the entity
- `@CreatedDate`, `@LastModifiedDate` and `@Version` for auditing purposes.
- Several validations to validate the input data.

## [Priority](https://github.com/ManuMyGit/CodingTutorials/blob/main/microservices/restapi/src/main/java/org/mjjaen/restapi/model/Priority.java)
Enum used to define the priority of the task.

## [Location](https://github.com/ManuMyGit/CodingTutorials/blob/main/microservices/restapi/src/main/java/org/mjjaen/restapi/model/Location.java)
Data model used to call the location API by IP in the CallingOtherServiceController controller. Annotation `@JsonProperty` is used when the JSON name of the response is different from the class field. The main reason is due to camel case naming convention followed in the class vs snake case one followed in the response of the API used.

# DTO
## [Dto](https://github.com/ManuMyGit/CodingTutorials/blob/main/microservices/restapi/src/main/java/org/mjjaen/restapi/dto/TaskDto.java)
The DTO is the class that interacts with the external calls. This layer is used to avoid exposing the data model externally. The only data not exposed in this case is the version field. It includes as well validation annotations as this object will be validated in the Controller layer, throwing the exception MethodArgumentNotValidException in case of not valid arguments. 

## [Dto Converter](https://github.com/ManuMyGit/CodingTutorials/blob/main/microservices/restapi/src/main/java/org/mjjaen/restapi/dto/TaskDtoConversion.java)
This class includes the logic to convert from Dto to data model and vice versa. It uses the ModelMapper class, bean that is defined in the general configuration.

# CONFIGURATION
## [Async configuration](https://github.com/ManuMyGit/CodingTutorials/blob/main/microservices/restapi/src/main/java/org/mjjaen/restapi/config/AsyncConfig.java)
This class defines all things necessary to manage asynchronous operations. It extends from AsyncConfigurer a class offered by Spring to help configure asynchronous operations:
- It uses the annotation `@EnableAsync` to enable async operations with the annotation `@Async`. This annotation is not necessary when async operations are manually created by using CompletableFuture and the Thread Pool defined.
- It defines an Executor bean to create the thread pool to manage async operations manually (without using `@Async`). The properties of the pool can be defined in the application.properties file.
- It defines four additional Executor (default, controller, service and repository) that are used in the methods annotated with `@Async` (depending on where they are defined).
- It defines a AsyncUncaughtExceptionHandler, a functional interface that helps manage uncaught exceptions on methods annotated with `@Async`. What the default implementation of this functional interface does is just to log the exception. We could develop our own implementation by creating a class that extends the AsyncUncaughtExceptionHandler interface.

## [Database configuration](https://github.com/ManuMyGit/CodingTutorials/blob/main/microservices/restapi/src/main/java/org/mjjaen/restapi/config/DataBaseConfig.java)
This class defines all things necessary to connect to the database. It extends the helper class AbstractMongoClientConfiguration to configure the connectivity. It gets the database URI and the name from the application.properties file. This class:
- Overrides the method `configureClientSettings` to configure the connection based on the URI.
- Overrides the method `getDatabaseName` to declare the database name.
- Declares a bean with name auditingDateTimeProvider for database auditing. This bean is used in the same class, where annotation `@EnableMongoAuditing` is used to enable Spring Data MonboDB auditing.

## [General configuration](https://github.com/ManuMyGit/CodingTutorials/blob/main/microservices/restapi/src/main/java/org/mjjaen/restapi/config/GeneralConfig.java)
This class defines general beans required for the application to work. It defines:
- A modelMapper bean to be used in the DTO conversion.

# EXCEPTION MANAGEMENT
There are three classes used for exception management:
- [DataNotFoundException](https://github.com/ManuMyGit/CodingTutorials/blob/main/microservices/restapi/src/main/java/org/mjjaen/restapi/exception/DataNotFoundException.java). Custom exception that is thrown when the data with a specific id does not exist in the database.
- [CustomError](https://github.com/ManuMyGit/CodingTutorials/blob/main/microservices/restapi/src/main/java/org/mjjaen/restapi/exception/CustomError.java). Object returned as API response when there is an error.
- [MyExceptionHandler](https://github.com/ManuMyGit/CodingTutorials/blob/main/microservices/restapi/src/main/java/org/mjjaen/restapi/exception/MyExceptionHandler.java). This class contains all the logic to manage the exceptions.

## Exception handler
The exception handle declares the annotation `@ControllerAdvice`. This annotation basically builds a wrapper around all the methods that fit the annotation. In this case, the annotation has been configured to act over all methods whose class is annotated with RestController.

Additionally, this class extends the class ResponseEntityExceptionHandler. This class offers a handy set of methods to manage different type of exceptions:
- Most common errors (HttpRequestMethodNotSupportedException, MethodArgumentNotValidException) are managed by the method handleException, that sets the proper status code for each type of error and derives the management to the method handleExceptionInternal that basically returns a null body request with the proper status.

The class has four different methods:
- `handleNotFoundException`: specific method to handle the custom exception DataNotFoundException. This method returns an object CustomError whose message is the id not found and the current datetime. HttpCode returned is 404.
- `handleMethodArgumentNotValidAtPersistanceLevel`: this method handles the exception ConstraintViolationException. This exception is thrown when the object (Data model) is validated at the persistence layer. The object CustomError includes the details of the validation as well as the current datetime. HttpCode returned is 400.
- `MethodArgumentNotValidException`: this method handles the exception MethodArgumentNotValidException. This exception is thrown when the object (DTO) is validated at the controller layer. The object CustomError includes the details of the validation as well as the current datetime. HttpCode returned is 400.
- `handleAllExceptions`: this method handles all other exceptions. HttpCode returned is 500.