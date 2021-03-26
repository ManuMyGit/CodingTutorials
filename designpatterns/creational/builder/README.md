# Definition
The Builder pattern is a classic Gang of Four creational design pattern. This pattern, similar to the other creational patterns, such as factory method and abstract factory, is concerned with the creation of objects. The builder pattern allows you to enforce a step-by-step process to construct a complex object as a finished product. In this pattern, the step-by-step construction process remains the same but the finished products can have different representations.

This pattern provides a build object which is used to construct a complex object called the product. It encapsulates the logic of constructing the different pieces of the product.

# Definition properties
- Type: creation of the object
- Problem: to create a complex object
- Solution: to create the object step by step

# Participants in the Factory Method Pattern
- Product (Task and TaskInternalBuilder in the example): a class that represents the product to create. If the constructor is only accesible by the builder, we're forcing to create the object by using the builder.
- Builder (TaskBuilder and TaskInternalBuilder.Builder in the example): the clas in charge of providing the methods to build the product.
