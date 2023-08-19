# INTRODUCTION
This tutorial is a basic tutorial about how to connect and use Spring Data Redis to work with a Redis database. This tutorial covers:
- Connectivity
- Basic example of value operations.
- Basic example of list operations.
- Basic example of set operations.
- Basic example of sorted set operations.
- Basic example of hash operations.
- Basic example of geospatial operations.
- Basic example of publisher/subscriber.

## Connectivity
This example covers a standalone configuration to a single node Redis database. Host, port, password, database number and timeouts can be configured in the application.properties. Connectivity can be found in the RedisConfig component. It contains the following beans:
- redisConnectionFactory: redis connection factory basic bean.
- employeeRedisTemplate: redis template to manage instances of Employee. A specific template was created to serialize the hash value by using Employee.class.
- customerRedisTemplate: redis template to manage instances of Customer. A specific template was created to serialize the hash value by using Customer.class.
- stringRedisTemplate: standard redis template to manage non-hash operations.
- pubSubTemplate: redis template to manage instances of Message. A specific template was created to serialize the hash value by using Message.class.
- channelTopic: channel topic for the pub/sub configuration.
- messageListener: bean defining the implementation of the subscriber (object and listener method).
- redisContainer: container providing asynchronous behaviour for Redis message listeners.

## Examples
This list of examples in conjunction with the full [Redis operations tutorial](https://github.com/ManuMyGit/CodingTutorials/blob/main/database/nosql/keyvalue/redis) will help you have the necessary knowledge to be able to build any Redis application.

### Value operations
Basic value operations like setIfAbscent, get and delete.

### List operations
Basic list operations like leftPush, rightPush, size and delete. In this example you can see how elements are stored in order and duplicates are allowed.

### Set operations
Basic set operations like add, size, difference and delete. In this example you can see how sets do not have duplicated values.

### Sorted set operations
Basic sorted set operations like add, rank, size and delete. In this exmaple, you can see how the rank of the elements does not match with the adding order due to the score.

### Hash operations
This example shows two different approaches to store POJOs in a database:
- Approach 1:
  - Employee object.
  - EmployeeRepository.
  - Data is stored in the following way:
    - Key: name of the entity.
    - Field: id of the entity.
    - Value: entity entire JSON.
- Approach 2:
  - Customer object.
  - CustomerRepository.
  - Data is stored in the following way:
    - Key: customer:idCustomer.
    - Field: customer property.
    - Value: customer property value.

CRUD operations in both repositories are different depending on the approach followed.

### Geospatial operations
Basic geospatial operations like add, distance, radius or delete.

### Publisher/Subscriber configuration
Basic example of a publisher publishing 1 message every half a second 3 times and a subscriber listening to and printing those messages.