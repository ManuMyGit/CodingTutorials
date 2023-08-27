# INTRODUCTION
Java 10 continued the tradition of enhancing the Java programming language with new features and improvements.

## Local Variable Type Inference
Java 10 introduced local variable type inference, allowing you to declare variables without explicitly specifying their types. This feature enhances code readability without sacrificing type safety. To declare such variables a new keyword was introduced: var.

```java
var name = "Java 10"; // Compiler infers the type as String
var numbers = List.of(1, 2, 3); // Compiler infers the type as List<Integer>
```

## Application Class-Data Sharing
Application Class-Data Sharing (AppCDS) is an optimization introduced in Java 10 to improve startup time and memory footprint of Java applications. It allows you to precompute and archive shared class metadata to be used by multiple JVM instances.

## Thread-Local Handshakes
Thread-Local Handshakes provide a mechanism for threads to asynchronously trigger actions on other threads. This feature is useful for scenarios where you need to interact with threads without performing expensive operations like stopping them.

## Enhanced Optional Class
Java 10 enhanced the Optional class with a few new methods:
- `Optional.orElseThrow()` with no arguments: Allows you to throw a custom exception when no value is present. 
- `Optional.stream()`: Converts the Optional value to a Stream.

## Time-Based Release Versioning
Starting from Java 10, the release versioning changed to a time-based scheme, with a new feature release every six months. This new versioning strategy aimed to provide more predictable and frequent updates to the Java platform.

## Deprecations and Removals
Java 10 also marked several features for deprecation and removal, including the javax.xml.ws and javax.xml.bind modules. It's important to be aware of these changes when migrating applications to newer versions of Java.