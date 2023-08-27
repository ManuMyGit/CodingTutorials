# INTRODUCTION
Java 9 introduced a variety of exciting features and enhancements that aimed to improve developer productivity, performance, and maintainability.

Let's explore each of these features in detail.

## Module system
Java 9 introduced the concept of modules, a way to encapsulate code and its dependencies into a single unit called a module. The module system addresses issues related to classpath and JAR hell by providing better control over access to packages and classes. Modules enable better modularity, improved security, and more efficient packaging.
- module-info.java: This file is used to declare a module, its dependencies, and what it exports to other modules.

## JShell: The Java REPL
JShell is an interactive Read-Eval-Print Loop (REPL) tool that allows you to experiment with Java code snippets directly in the command line. It's a great way to quickly test ideas, learn Java, and prototype code without the need to create full-fledged programs.
- Launch JShell: Open your terminal and type jshell to start the interactive shell.
- Enter Java code: Type in Java expressions, statements, or declarations, and see immediate results.

## Improved Process API
Java 9 enhanced the Process API to allow better control and management of native processes. New classes and methods were introduced to handle processes more effectively.
- ProcessHandle: Represents a native process and provides methods to get information about the process, monitor processes, and manage their lifecycle.
- ProcessHandle.onExit(): Allows you to define actions to perform when a process exits.

## Collection Factory Methods
Java 9 introduced convenient factory methods for creating immutable collections. These methods simplify the process of creating lists, sets, and maps with fewer lines of code. 
- List.of(), Set.of(), and Map.of(): Create compact, immutable collections with minimal boilerplate.

## Private Methods in Interfaces
Interfaces in Java 9 can now have private methods, which can be used to share common code among default methods within the same interface. This improves code organization and reduces code duplication.

## Try-With-Resources Improvements
The try-with-resources statement automatically closes all the resources at the end of the statement. A resource is an object to be closed at the end of the program. In Java 9, the try-with-resources statement was enhanced to allow effectively final variables to be used in the resource declaration. This reduces the need for additional code blocks.

## Stream API Inhancements
The Stream API received a few enhancements in Java 9:
- Stream.iterate(): Generates an infinite sequential stream with an initial element and a function to generate subsequent elements.
- Stream.dropWhile() and Stream.takeWhile(): Allow you to remove or take elements from a stream until a certain condition is met.

## HTTP/2 Client
Java 9 introduced a new HTTP client API that supports HTTP/2 and WebSocket. This new client is more efficient and offers better performance compared to the old HttpURLConnection.

## New Version-String Scheme
Java 9 introduced a new version-string scheme that adheres to Semantic Versioning (SemVer). This makes it easier to understand the versioning of the Java platform and its components.

## Deprecations and Removals
Java 9 marked several features for deprecation and removal, including the Applet API, the Security Manager for applets, and various other less-used classes and methods.