# Definition
What is Singleton? The singleton pattern is one of the Gang of Four creational design patterns. In software engineering, this is a term that implies a class which can only be instantiated once, and a global point of access to that instance is provided. In the Java programming language, there are a few standard implementations of the singleton pattern.

# Definition properties
- Type: creation of the object
- Problem: just there can be one instance of a class
- Solution: to ensure the unique creation of the instance of a class

# Implementations
- SingletonLazy: the instance will be created when you call getInstance method.
- SingletonEager: the instance will be created at the starting of the application whether you need it or not.
- SingletonBillPugh: before Java 5 there were several problems with the memory and the first aproximations of Singleton used to fail in certain scenes where lots of threads tried to get the instance simultaneously. Bill Pugh did a different aproach of creating a Singleton by using a inner static helper class.
- SingletonEnum: it is easy to modify the visibility of constructors of a singleton through reflection, making the constructor public what allow to create as many instances as you want. To solver that problem, Joshua Bloch suggested to use an Enum type to implement this pattern as Java ensures whatever enum value is unique in a Java program. As enum types are publicly accessible, it is singleton pattern. The problem is that enum type is a little rigid. For example, it is not possible lazy init.
- SingletonThreadSafe: this implementation is like SingletonLazy but it is thread-safe. You can use one just one check (instance == null) or double check and to synchronize after first checking (what increases the performance).
- SingletonStaticBlock: similar to SingletonEager, but the creation is inside a static block with exceptions management.
- SingletonSerializable: distributed systems need sometimes the Singleton class to implement Serializable to save its status in a system file to retrieve in a moment in the future. The first approach could be to implement this pattern with any method already explained. But it has a problem because every time a serialized object is deserialized a new instance will be created. To solve that problem you need to add the readResolve() method.