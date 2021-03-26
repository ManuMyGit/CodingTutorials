# Definition
The abstract factory pattern is one of the classic Gang of Four creational design patterns used to create families of objects, where the objects of a family are designed to work together. In the abstract factory pattern you provide an interface to create families of related or dependent objects, but you do not specify the concrete classes of the objects to create. From the client point of view, it means that a client can create a family of related objects without knowing about the object definitions and their concrete class names.

It is easy to confuse the abstract factory pattern with the factory method pattern because both design patterns deal with object creation. Both the patterns advocates the Object Oriented Programming (OOP) principle “Program to an interface, not an implementation” to abstract how the objects are created. Both design patterns help in creating client code that is loosely-coupled with object creation code, but despite the similarities, and the fact that both the patterns are often used together, they do have distinct differences.

Abstract factory adds another level of abstraction to factory method. While factory method abstracts the way objects are created, abstract factory abstracts how the factories are created. The factories in turn abstracts the way objects are created. You will often hear the abstract factory design pattern referred to as a “factory of factories“.

# Definition properties
- Type: creation of the object.
- Problem: creating entire product families without specifying their concrete classes.
- Solution: defines an interface for creating all distinct products but leaves the actual product creation to concrete factory classes.

# Participants in the Factory Method Pattern
- AbstractProduct (Task and Car in the example): an interface or an abstract class whose subclasses are instantiated by the abstract factory objects.
- ConcreteProduct (ComputerTask, HomeTask, WorkTask, ManualCar and AutomaticCar in the example): the concrete subclasses that implement/extend AbstractProduct. The abstract factory objects instantiate these subclasses.
- AbstractFactory (AbstractFactory in the example): an interface or an abstract class whose subclasses instantiate a family of AbstractProduct objects.
- ConcreteFactory (TaskFactory and CarFactory in the example): the concrete subclasses that implement/extend AbstractFactory. An object of this subclass instantiates a family of AbstractProduct objects.
- Client: Uses AbstractFactory to get AbstractProduct objects. 