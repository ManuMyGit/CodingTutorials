# Definition
What is Factory Method pattern? This pattern is a classic Gang of Four creational design pattern that is concerned with the creation of objects in an application. As the name suggests, the factory method pattern makes use of classes that acts as factories to create objects. This pattern favors method invocation instead of making direct constructor calls to create objects. In the factory method pattern, you provide an interface, which can be a Java interface or an abstract class to create objects. A factory method in the interface defers the object creation to one or more concrete subclasses at run time. The subclasses implement the factory method to select the class whose objects need to be created.

# Definition properties
- Type: creation of the object.
- Problem: dependency of objects creation.
- Solution: create a factory to create objects. Abstract product creation.

# Participants in the Factory Method Pattern
- Product (Task in the example): is an interface or an abstract class whose subclasses are instantiated by the factory method.
- ConcreteProduct (ComputerTask, WorkTask and HomeTask in the example): are the concrete subclasses that implement/extend Product. The factory method instantiates these subclasses.
- Creator (BaseTaskFactoryMethod in the example): is an interface or an abstract class that declares the factory method, which returns an object of type Product.
- ConcreteCreator (TaskFactoryMethod in the example): is a concrete class that implements the factory method to create and return a ConcreteProduct to Client.
- Client: Asks the Creator for a Product.

# Factory Method vs Simple Factory
A Simple Factory is a Factory Method which only create objects, with no more logic. With Factory Method, you don't only abstract product creation, but take on step further. 