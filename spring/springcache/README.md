# INTRODUCTION
Caching is a technique that involves storing frequently accessed data in memory or another fast storage medium to reduce the need for repeated expensive computations or database queries. Caching enhances application performance by serving previously computed results quickly.

# BASICS OF SPRING BOOT CACHE
Spring Boot provides a caching abstraction that simplifies caching operations. The caching abstraction relies on cache managers to handle caching operations. Cache managers manage one or more named caches, each of which stores cached data.

Spring Boot provides caching annotations that make it easy to add caching behavior to your methods:
- ```@Cacheable```: Indicates that a method result should be cached. 
- ```@CachePut```: Updates the cache with the result of the method invocation. 
- ```@CacheEvict```: Removes entries from the cache.

# USE SPRING BOOT CACHE
To use Spring Boot Cache:
1. Cache needs to be enabled with ```@EnableCaching```.
2. Redis connection needs to be configured.
3. Cache needs to be configured.
4. Use the following annotations:
   1. ```@Cacheable```: get methods.
   2. ```@CacheEvict```: delete methods.
   3. ```@CachePut```: update methods.

## Advanced cache features
### Conditional caching
You can conditionally enable caching based on certain criteria using SpEL expressions in cache annotations.
```java
@Cacheable(value = "myCache", condition = "#enabled")
public String getCachedData(String key, boolean enabled) {
   // ...
}
```

### Cache managers
Spring Boot allows you to configure multiple cache managers and assign them to different cache annotations.

### Cache Serialization
When using distributed cache providers like Redis, ensure that your cached objects are serializable.