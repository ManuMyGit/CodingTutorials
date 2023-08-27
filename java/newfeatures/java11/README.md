# INTRODUCTION
Java 11, also known as Java 18.9, introduced several new features and enhancements to the Java programming language.

## Local-Variable Syntax for Lambda Parameters
Java 11 introduced the ability to use the var keyword in lambda expressions, allowing for more concise code when defining lambda parameters.
```java
BiFunction<Integer, Integer, Integer> add = (var a, var b) -> a + b;
```

## HTTP Client API (Standard)
The HTTP Client API, which was introduced as an incubator feature in Java 9, became a standard feature in Java 11. It provides a more modern and flexible way to interact with HTTP resources, including support for HTTP/1.1, HTTP/2, and WebSocket.

## Nest-Based Access Control
Nest-based access control allows classes that are logically part of the same group to access each other's private members. This feature enhances security and encapsulation in complex class hierarchies.

## Launch Single-File Source-Code Programs
Java 11 introduced a convenient way to run single-file Java programs without the need to explicitly compile them. You can now execute a Java source file directly using the java command.
```shell
java HelloWorld.java
```

## String Methods for Handling isBlank, lines, and strip
Java 11 added several methods to the String class to enhance string manipulation:
- `isBlank()`: Checks if a string is empty or contains only whitespace.
- `lines()`: Returns a stream of lines from a multi-line string.
- `strip()`, stripLeading(), and stripTrailing(): Remove whitespace from the string.

## Epsilon: A No-Op Garbage Collector
The Epsilon garbage collector was introduced as an experimental feature in Java 11. It's a no-op garbage collector that's useful for performance testing, as it doesn't perform any actual memory reclamation. This can help isolate and analyze the performance characteristics of other parts of your application.

## New File Methods for Reading and Writing Strings
Java 11 introduced new methods in the Files class that make it easier to read and write strings to files:
- readString(Path path): Reads the content of a file into a string.
- writeString(Path path, CharSequence csq): Writes a string to a file.

## Deprecations and Removals
As with any new release, Java 11 marked certain features as deprecated or removed, including the SecurityManager class and the Pack200 tools. It's crucial to review these changes when migrating applications to Java 11.