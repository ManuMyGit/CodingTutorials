# INTRODUCTION
Lettuce is a high-performance and feature-rich Redis client library for Java. It supports various Redis features, including data structures, transactions, and pub/sub. Lettuce is well-suited for building scalable and efficient applications that interact with Redis databases.

# SETTING UP LETTUCE
To use Lettuce in your Java project, you need to include the Lettuce dependency in your build configuration. In this example, we are using Maven. The required dependency can be found in this [pom.xml]().

# LETTUCE CAPABILITIES
## Configuration
Lettuce offers three different ways to operate:
- Synchronous
- Asynchronous
- Reactive

## Operations
As a Redis Client, Lettuce supports all operations and data types that the Redis server offers. A tutorial to Redis can be found [here](). 

# ABOUT THIS EXAMPLE
This example shows four different things:
- How to configure Redis.
- Basic example of synchronous operation (set + get + delete).
- Basic example of asynchronous operation (set + get + delete).
- Basic example of reactive operation (set + get + delete).