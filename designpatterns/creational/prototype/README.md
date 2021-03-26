# Definition
The prototype pattern is a classic Gang of Four creational pattern, and similar to the other members of the creational pattern family: singleton , factory method, abstract factory, and builder, prototype is also concerned with object creation, but with a difference. Using the prototype pattern, you do not create a new object for each client requesting the object. Instead, you start by creating a single object, called a prototype and make copies of it for each client requesting the object. In Java, this is achieved through object cloning, a way to make a copy of an object with the same state as the original object. 

But why create copies if we can create new objects through constructor calls, which is much simpler? Most of the time you will not need to create copies of objects. But, as you move into enterprise application development where application performance is critical, you will encounter situations where construction of an object involves time consuming operations, such as network communication, database reads, and disk I/O. If a large number of such objects needs to be created, you can avoid repeating those steps for each object by initially creating a prototype and then making copies of it.

# Shallow Copy Vs Deep Copy
Java provides the Cloneable interface to mark objects that permit cloning. This interface is a marker interface and therefore does not contain any method declaration. When implemented in a class, Cloneable marks that objects of the class can be cloned. To perform cloning, you need to call the protected clone() method of the Object class through a call to super.clone().

A call to super.clone() performs a shallow copy where all the fields values of the original object are copied to the new object. If a field value is a primitive type, a shallow copy copies the value of the primitive type. But, if a field value is a reference type, then only the reference is copied, and not the referred object itself. Therefore, both the original and its clone refer to the same object and if either one modifies the referred object, the modification will be visible to the other

When using the prototype pattern, should we go for shallow copy or for deep copy? There is no hard and fast rule, it all depends on the requirement. If an object has only primitive fields or immutable objects (whose state cannot change, once created), use a shallow copy. When the object has references to other mutable objects, then either choose shallow copy or deep copy. For example, if the references are not modified anytime, avoid deep copy and go for shallow copy.

# Definition properties
- Type: creation of the object
- Problem: the cost of the creation of an object is quite high
- Solution: to create the new object from another object called prototype

# Participants in the Factory Method Pattern
- Prototype (Customer in the example): Java interface or abstract class that defines the contract for classes that permits cloning of its objects.
- ConcretePrototype (CustomerDeep and CustomerShallow in the example): classes that provide operations to clone its objects.
- PrototypeManager (CustomerPrototypeManager in the example): class that implements a registry to manage available prototypes for clients. On a client request, this class creates a copy of a prototype.
- Client: asks the PrototypeManager for copies of prototypes.