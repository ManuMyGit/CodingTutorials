# What is Spring Data MongoDB?
Spring Data MongoDB is a part of the Spring Data project, which aims to provide a consistent and efficient way to work with various data stores. Spring Data MongoDB specifically focuses on integrating MongoDB, a NoSQL document database, into Spring-based applications. It offers abstractions and utilities to simplify database interactions, enabling developers to focus more on application logic and less on low-level database details.

Advantages of Using Spring Data MongoDB;
- Simplified Integration: Spring Data MongoDB abstracts away the complexities of interacting with MongoDB, providing a high-level API that aligns with Spring's programming model.
- Increased Productivity: Developers can work with MongoDB using familiar Spring concepts, reducing the learning curve and speeding up development.
- Flexibility: Spring Data MongoDB supports various ways of querying data, ranging from simple query methods to complex aggregation operations.
- Scalability: MongoDB's natural scalability is complemented by Spring Data MongoDB's features, making it suitable for both small-scale applications and large, distributed systems.

A complete tutorial about MongoDB can be found [here](https://github.com/ManuMyGit/CodingTutorials/tree/main/database/nosql/document/mongodb).

# Configuration
## Single database
There are several ways to configure the connection, the one of the easiest one is by extending the class AbstractMongoClientConfiguration:
```java
public class DataBaseConfig extends AbstractMongoClientConfiguration  {
    @Value("${spring.data.mongodb.uri}")
    private String uri;

    @Value("${spring.data.mongodb.database}")
    private String db;

    @Override
    protected void configureClientSettings(MongoClientSettings.Builder builder) {
        final ConnectionString connectionString = new ConnectionString(uri);
        builder.applyConnectionString(connectionString);
    }

    @Override
    protected String getDatabaseName() {
        return db;
    }
}
```

## Multiple database
When multiple databases need to be configured, we can create a MongoTemplate for each one:
```java
@Configuration
public class MultipleDbConfig {

    @Bean(name = "primaryMongoTemplate")
    public MongoTemplate primaryMongoTemplate() {
        return new MongoTemplate(MongoClients.create(), "primaryDb");
    }

    @Bean(name = "secondaryMongoTemplate")
    public MongoTemplate secondaryMongoTemplate() {
        return new MongoTemplate(MongoClients.create(), "secondaryDb");
    }
}
```

# Documents
We use the following annotations to define a document:
- `@Document`: basic annotation to tell Spring Data this class is a document. Spring Data needs to be aware of the entity.
  - The document name defaults to the name of the class. We can change its name using the name element: `@Document(collection="users")`.
- `@Id`: this annotation defines the primary key.
- `@DBRef`: applied at the field to indicate it is to be stored using a com.mongodb.DBRef. The mapping framework doesn't have to store child objects embedded within the document. You can also store them separately and use a DBRef to refer to that document. When the object is loaded from MongoDB, those references will be eagerly resolved and you will get back a mapped object that looks the same as if it had been stored embedded within your master document.
  - db: default "".
  - lazy: default false.
- `@Indexed`: applied at the field level to describe how to index the field. Properties:
  - unique: default false.
  - direction: default ASCENDING.
  - name.
  - useGeneratedName: default false.
  - background: default false.
  - expireAfterSeconds: default -1.
  - expireAfter.
  - partialFilter.
- `@CompoundIndex`: applied at the type level to declare Compound Indexes.
```java
@Document
@CompoundIndexes({
    @CompoundIndex(name = "age_idx", def = "{'lastName': 1, 'age': -1}")
})
public class Person {
    ...
}
```
- `@GeoSpatialIndexed`: applied at the field level to describe how to geoindex the field. 
- `@Transient`: by default all private fields are mapped to the document, this annotation excludes the field where it is applied from being stored in the database 
- `@PersistenceConstructor`: marks a given constructor - even a package protected one - to use when instantiating the object from the database. Constructor arguments are mapped by name to the key values in the retrieved DBObject. 
- `@Value`: this annotation is part of the Spring Framework. Within the mapping framework it can be applied to constructor arguments. This lets you use a Spring Expression Language statement to transform a key's value retrieved in the database before it is used to construct a domain object. 
- `@Field`: applied at the field level and described the name of the field as it will be represented in the MongoDB BSON document thus allowing the name to be different than the fieldname of the class.

## Overriding default mapping with custom converters
In order to have more fine grained control over the mapping process you can register Spring converters with the MongoConverter implementations such as the MappingMongoConverter. The MappingMongoConverter checks to see if there are any Spring converters that can handle a specific class before attempting to map the object itself. To 'hijack' the normal mapping strategies of the MappingMongoConverter, perhaps for increased performance or other custom mapping needs, you first need to create an implementation of the Spring Converter interface and then register it with the MappingConverter.

An example implementation of the Converter that converts from a Person object to a com.mongodb.DBObject is shown below:
```java
@WritingConverter
public class PersonWriteConverter implements Converter<Person, DBObject> {
  public DBObject convert(Person source) {
    DBObject dbo = new BasicDBObject();
    dbo.put("_id", source.getId());
    dbo.put("name", source.getFirstName());
    dbo.put("age", source.getAge());
    return dbo;
  }
}
```

An example implemention of a Converter that converts from a DBObject ot a Person object is shownn below:
```java
@ReadingConverter
public class PersonReadConverter implements Converter<DBObject, Person> {
  public Person convert(DBObject source) {
    Person p = new Person((ObjectId) source.get("_id"), (String) source.get("name"));
    p.setAge((Integer) source.get("age"));
    return p;
  }
}
```

Once converters are ready, we need to register the converters in the Spring context:
```java
@Bean
public MongoCustomConversions mongoCustomConversions() {
    return new MongoCustomConversions(
        Arrays.asList(
            new MyClassToBytesConverter(),
            new BytesToMyClassConverter()));
  }
```

# Events
Spring Data MongoDB publishes some very useful life cycle events:
- onBeforeConvert.
- onBeforeSave.
- onAfterSave.
- onAfterLoad.
- onAfterConvert.
- onAfterDelete.
- onBeforeDelete.

To intercept one of the events, we need to register a subclass of AbstractMappingEventListener and override one of the methods here. When the event is dispatched, our listener will be called and domain object passed in.

Lifecycle events are only emitted for root level types. Complex types used as properties within a document root are not subject to event publication unless they are document references annotated with @DBRef.

# Relationships
There are several ways to implement relationships in Mongo:
1. Embedding the related entity
2. Using manual references
3. Using dbref

## Embedding Documents
In this type of relationship, the related object is stored as part of the main object in the same collection. For instance:

In this example, address is not declared as a Document, hence Mongo stores the address as part of the User document. Since Address is not a standard type, we need to register a converter to tell Spring Data how to store and retrieve the address object.

## Manual references
In this type of relationship, the related object id is stored in the main object. This would be the equivalent of SQL relationship.

## DBRef
DBRef is similar to manual references. It is achieved by using the annotation `@DBRef`, but it is more complex than manual references as it can store the database and the collection name of the referenced entity. For most of the cases, embedded relationship and manual references will suffice. Remember MongoDB is not a relational database.

## One-to-One, One-to-Many, Many-to-Many Relationships
These three type of relationships can be achieved by using any of the three techniques mentioned above:
- One-to-One:
  - We can embed the related object within the original object.
  - We can store the related object id within the original object.
  - We can store the related object ref within the original object.
- One-to-Many:
  - Same as above, with the difference than from the related object, it could see multiple different main objects related to the same reference object.
- Many-to-Many:
  - Same as above, but the multi relationship can be seen from both sides.

# Transactions
## Transactional Operations
Spring Data MongoDB supports transactions when using a compatible version of MongoDB and a suitable transaction manager. Use @Transactional annotations to define transactional methods and define a transaction manager bean:
```java
@Bean
MongoTransactionManager transactionManager(MongoDatabaseFactory dbFactory) {
    return new MongoTransactionManager(dbFactory);
}
```

The @transactional annotation offers, among others, the following customization:
- propagation: 
  - REQUIRED: to tell Spring to either join an active transaction or to start a new one if the method gets called without a transaction. This is the default behavior.
  - SUPPORTS: to join an activate transaction if one exists. If the method gets called without an active transaction, this method will be executed without a transactional context.
  - MANDATORY: to join an activate transaction if one exists or to throw an Exception if the method gets called without an active transaction.
  - REQUIRES_NEW: to always start a new transaction for this method. If the method gets called with an active transaction, that transaction gets suspended until this method got executed.
  - NOT_SUPPORTED: to suspend an active transaction and to execute the method without any transactional context.
  - NEVER: to throw an Exception if the method gets called in the context of an active transaction.
  - NESTED: to start a new transaction if the method gets called without an active transaction. If it gets called with an active transaction, Spring sets a savepoint and rolls back to that savepoint if an Exception occurs.
- isolation:
  - DEFAULT: 
  - READ_UNCOMMITTED: allows dirty reads, non-repeatable reads, and phantom reads.
  - READ_COMMITTED: allows non-repeatable reads and phantom reads.
  - REPEATABLE_READ: allows phantom reads.
  - SERIALIZABLE: provides the highest level of isolation, preventing all anomalies.
- timeout: number of ms to wait for the operation to be done.
- readOnly: used to indicate that a particular method is read only.

Transactions are undoubtedly the most exciting new feature in MongoDB 4.0. But unfortunately, most tools for installing and running MongoDB start a standalone server as opposed to a replica set. If you try to start a session on a standalone server, you'll get the error "Transaction numbers are only allowed on a replica set member or mongos"

### Nested transactions
Spring allows nested transactions, where a method can start a new transaction or participate in an existing one. You can control how nested transactions interact using the propagation attribute of the @Transactional annotation. Common propagation values include REQUIRED, REQUIRES_NEW, NESTED, and more.

## Handling Errors and Exceptions
Spring Data MongoDB provides mechanisms to handle exceptions, such as MongoException, gracefully. You can catch exceptions, log errors, and provide user-friendly error messages.

### Rollback and Commit
When an unchecked exception (a subclass of RuntimeException) is thrown within a transactional method, the transaction is automatically rolled back by default. This ensures that any changes made within the method are discarded. Checked exceptions, on the other hand, generally do not trigger automatic rollback.

You can customize the behavior of transactions in the presence of exceptions using the @Transactional annotation's properties. For example:
- rollbackFor: Specify exceptions that should trigger a rollback.
- noRollbackFor: Specify exceptions that should not trigger a rollback.
- rollbackForClassName: Specify exception class names for rollback triggers.
- noRollbackForClassName: Specify exception class names for non-rollback triggers.

# Spring Data Mongo Features
## Repositories
The MongoRepository interface offers the most common CRUD operations, either directly or inherited from extended interfaces:
- `count`
- `delete`
- `deleteAll`
- `deleteById`
- `exists`
- `findAll`
- `findAllById`
- `findById`
- `findOne`
- `insert`
- `saveAll`

For further information about Spring Data, please visit [Spring Data JPA](https://github.com/ManuMyGit/CodingTutorials/tree/main/spring/springdata/jpa) tutorial since the capabilities are pretty similar.

## Mongo template
MongoTemplate is a class which provides us different handy methods to work on various DB operations. It offers full customization of operations, but it is advised to be used if:
1. It is not a standard operation offered by the MongoRepository interface.
2. It is not an operation that can be built based on Spring Data naming convention.

## Saving documents
There are two main methods to store objects as part of MongoRepository interface:
- `insert`: Insert an object. If there is an existing document with the same id then an error is generated. 
  - `insertAll`: Takes a Collection of objects as the first parameter. This method inspects each object and inserts it to the appropriate collection based on the rules specified above. 
- `save`: Save the object overwriting any object that might exist with the same id. This is the method could used to update objects.

## Updating documents
Mongo template offers a few methods to update an object:
- `updateFirst`: updates the first document that matches the query document criteria with the provided updated document.
- `updateMulti`: Updates all objects that match the query document criteria with the provided updated document.
- `findAndModify`: update a document and return either the old or newly updated document in a single operation. MongoTemplate provides a findAndModify method that takes Query and Update classes and converts from DBObject to your POJOs.

As mentioned before, when using MongoRepository interface, the `save` method can be used to update an object.

## Query methods
### Basic query with Spring Data
There are several  ways to query information:
1. Use the find operations offered by MongoRepository.
2. Build interface operations based on Spring Data naming convention.
3. Use @Query.
4. Use Mongo Template.

### Basic query operations offered by MongoRepository
- `findAll`
- `findOne`
- `findById`
- `count`
- `exists`

### Query operations based on naming convention
Spring offers the following criterias:
- `Distinct`. For instance, `findDistinctByLastnameAndFirstname`, which is equivalent to the SQL query `select distinct …​ where x.lastname = ?1 and x.firstname = ?2`.
- `And`. For instance, `findByLastnameAndFirstname`, which is equivalent to the SQL query `... where x.lastname = ?1 and x.firstname = ?2`.
- `Or`. For instance, `findByLastnameOrFirstname`, which is equivalent to the SQL query `… where x.lastname = ?1 or x.firstname = ?2`.
- `Is, Equals`. For instance, `findByFirstname, findByFirstnameIs, findByFirstnameEquals`, which are equivalent to the SQL query `… where x.firstname = ?1`.
- `Between`. For instance, `findByStartDateBetween`, which is equivalent to the SQL query `… where x.startDate between ?1 and ?2`.
- `LessThan`. For instance, `findByAgeLessThan`, which is equivalent to the SQL query `… where x.age < ?1`.
- `LessThanEqual`. For instance, `findByAgeLessThanEqual`, which is equivalent to the SQL query ` … where x.age <= ?1`.
- `GreaterThan`. For instance, `findByAgeGreaterThan`, which is equivalent to the SQL query ` … where x.age > ?1`.
- `GreaterThanEqual`. For instance, `findByAgeGreaterThanEqual`, which is equivalent to the SQL query ` … where x.age >= ?1`.
- `After`. For instance, `findByStartDateAfter`, which is equivalent to the SQL query ` … where x.startDate > ?1`.
- `Before`. For instance, `findByStartDateBefore`, which is equivalent to the SQL query ` … where x.startDate < ?1`.
- `IsNull, Null`. For instance, `findByAge(Is)Null`, which is equivalent to the SQL query ` … where x.age is null`.
- `IsNotNull, NotNull`. For instance, `findByAge(Is)NotNull`, which is equivalent to the SQL query ` … where x.age not null`.
- `Like`. For instance, `findByFirstnameLike`, which is equivalent to the SQL query ` … where x.firstname like ?1`. We can use the character '%' like we do in SQL as part of regular expression.
- `NotLike`. For instance, `findByFirstnameNotLike`, which is equivalent to the SQL query ` … where x.firstname not like ?1`. We can use the character '%' like we do in SQL as part of regular expression.
- `StartsWith, StartingWith, IsStartingWith`. For instance, `findByFirstnameStartsWith, findByFirstnameStartingWith, findByFirstnameIsStartingWith`, which is equivalent to the SQL query ` … where x.firstname like ?1` (parameter bound with appended %).
- `EndsWith, EndingWith, IsEndingWith`. For instance, `findByFirstnameEndsWith, findByFirstnameEndingWith, findByFirstnameIsEndingWith`, which is equivalent to the SQL query ` … where x.firstname like ?1` (parameter bound with prepended %).
- `Containing, IsContaining`. For instance, `findByFirstnameContaining, findByFirstnameIsContaining`, which is equivalent to the SQL query ` … where x.firstname like ?1` (parameter bound wrapped in %).
- `OrderBy`. For instance, `findByAgeOrderByLastnameDesc`, which is equivalent to the SQL query ` … where x.age = ?1 order by x.lastname desc`.
- `Not`. For instance, `findByLastnameNot`, which is equivalent to the SQL query ` … where x.lastname <> ?1`.
- `In`. For instance, `findByAgeIn(Collection<Age> ages)`, which is equivalent to the SQL query ` … where x.age in ?1`.
- `NotIn`. For instance, `findByAgeNotIn(Collection<Age> ages)`, which is equivalent to the SQL query ` … where x.age not in ?1`.
- `True`. For instance, `findByActiveTrue()`, which is equivalent to the SQL query ` … where x.active = true`.
- `False`. For instance, `findByActiveFalse()`, which is equivalent to the SQL query ` … where x.active = false`.
- `IgnoreCase`. For instance, `findByFirstnameIgnoreCase`, which is equivalent to the SQL query ` … where UPPER(x.firstname) = UPPER(?1)`.

### Basic query with Mongo Template
We can use the object `BasicQuery` (which extends the Query object) to build custom queries:
```java
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import org.springframework.data.mongodb.core.query.BasicQuery;

BasicQuery query = new BasicQuery("{ age : { $lt : 50 }, accounts.balance : { $gt : 1000.00 }}");
List<Person> result = mongoTemplate.find(query, Person.class);
List<Person> result = mongoTemplate.find(query(where("age").lt(50).and("accounts.balance").gt(1000.00d)), Person.class);
```

Some of the methods offered by the class Criteria are:
- `all (Object o)`: creates a criterion using the $all operator 
- `and (String key)`: adds a chained Criteria with the specified key to the current Criteria and retuns the newly created one. 
- `andOperator(Criteria... criteria)`: creates an and query using the $and operator for all of the provided criteria (requires MongoDB 2.0 or later)
- `elemMatch (Criteria c)`: creates a criterion using the $elemMatch operator
- `exists (boolean b)`: creates a criterion using the $exists operator
- `gt (Object o)`: creates a criterion using the $gt operator
- `gte (Object o)`: creates a criterion using the $gte operator
- `in (Object... o)`: creates a criterion using the $in operator for a varargs argument.
- `in (Collection<?> collection)`: creates a criterion using the $in operator using a collection
- `is (Object o)`: creates a criterion using the $is operator
- `lt (Object o)`: creates a criterion using the $lt operator
- `lte (Object o)`: creates a criterion using the $lte operator
- `mod (Number value, Number remainder)`: creates a criterion using the $mod operator
- `ne (Object o)`: creates a criterion using the $ne operator
- `nin (Object... o)`: creates a criterion using the $nin operator
- `norOperator (Criteria... criteria)`: creates an nor query using the $nor operator for all of the provided criteria
- `not ()`: creates a criterion using the $not meta operator which affects the clause directly following
- `orOperator (Criteria... criteria)`: creates an or query using the $or operator for all of the provided criteria
- `regex (String re)`: creates a criterion using a $regex
- `size (int s)`: creates a criterion using the $size operator
- `type (int t)`: creates a criterion using the $type operator

### @Query
If we can't represent a query with the help of a method name or criteria, we can do something more low level using the @Query annotation.

This method allows su to overwrite the query created by the naming convention with some specific behaviour:
```java
@Query("{ 'name' : ?0 }")
List<Book> findBooksByName(String name);

@Query("{ 'name' : { $regex: ?0 } }")
List<User> findBooksByRegexName(String regexp);
```

To know more about the query capabilities offered by Mongo, please see the [MongoDB](https://github.com/ManuMyGit/CodingTutorials/tree/main/database/nosql/document/mongodb) tutorial.

### Projections
There are several ways to achieve projections in Spring Data MongoDB:
1. Use the methods `include` and `exclude` offered by MongoTemplate.
```java
BasicQuery query = new BasicQuery("{ age : { $lt : 50 }, accounts.balance : { $gt : 1000.00 }}");
query.fields().include("name").exclude("_id");
List<Book> result = mongoTemplate.find(query, Book.class);
```
2. Projections Using @Query
```java
@Query(value="{ 'name' : ?0 }", fields="{name : 1, _id : 0}")
List<Book> findBooksByNameExcludeId(String name);
```

### Aggregations
Aggregations in Spring Data MongoDB allow you to perform advanced data processing and analysis operations on your MongoDB collections. Aggregations are similar to SQL's GROUP BY clause but offer much more flexibility and power for working with your data. Spring Data MongoDB provides an abstraction for the MongoDB Aggregation Framework, allowing you to create and execute complex aggregation pipelines using Java code. Here's how aggregations work in Spring Data MongoDB:
1. Understand Aggregation Pipeline: the MongoDB Aggregation Framework works with a concept called an "aggregation pipeline." A pipeline consists of multiple stages, each representing a data transformation or analysis step. Stages are executed sequentially, and the output of one stage becomes the input for the next. 
2. Create Aggregation Operations: in Spring Data MongoDB, you can create aggregation pipelines using the Aggregation class. Each aggregation stage corresponds to a method provided by the Aggregation class. You can chain these methods to build the complete aggregation pipeline. 
3. Map to Java Objects: spring Data MongoDB allows you to map the results of aggregation queries to Java objects using projections. Projections define how the aggregation results should be transformed into Java objects. You can use projections to create DTOs (Data Transfer Objects) or domain objects that represent the aggregated data. 
4. Execute the Aggregation: once you've defined the aggregation pipeline, you can execute it using the MongoTemplate or a custom repository interface. The results of the aggregation are returned as a list of projected objects or raw documents, depending on your configuration.

See the following example:
```java
Aggregation aggregation = Aggregation.newAggregation(
        Aggregation.group("country").count().as("userCount")
);
AggregationResults<CountryUserCount> results = mongoTemplate.aggregate(aggregation, "users", CountryUserCount.class);
return results.getMappedResults();
```

For further information about how aggregations work in Mongo, please see the [MongoDB](https://github.com/ManuMyGit/CodingTutorials/tree/main/database/nosql/document/mongodb) tutorial.

## Auditing
Spring Data MongoDB supports auditing by adding `@CreatedDate`, `@LastModifiedDate`, and `@CreatedBy` annotations to audit fields in your document models. Spring Data MongoDB supports optimistic concurrency control (`@Version`), allowing you to detect and handle concurrent updates to the same document.

Auditing is enabled by using the annotation `@EnableMongoAuditing`. 

```java
@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {
  @Id
  private String userId;
  private String name;
  private Date creationDate = new Date();
  private Map<String, String> userSettings = new HashMap<>();
  @CreatedDate
  private OffsetDateTime dateCreated;
  @LastModifiedDate
  private OffsetDateTime lastUpdated;
  @Version
  private Integer version;
}
```

Once the entity is configured, the only thing we need to do is to tell Spring to activate the auditing with the annotation `@EnableMongoAuditing` in conjunction with the annotation `@EnableMongoRepositories`.
```java
@Configuration
@EnableMongoRepositories
@EnableMongoAuditing(dateTimeProviderRef = "auditingDateTimeProvider")
public class DataBaseConfig extends AbstractMongoClientConfiguration  {
    ...
    @Bean(name = "auditingDateTimeProvider")
    public DateTimeProvider dateTimeProvider() {
        return () -> Optional.of(OffsetDateTime.now());
    }
    ...
}
```