# Definitions
- Java provides certain data structures with its own properties. Some of them are:
	- List
	- Set
	- Queue
	- ...

# Collection interface
- It is one of the most important interfaces of Java.
- A collection is a group of elements which can be iterated and whose size you can know.
- This interface is extended by other interfaces, such as List or Queue.
- Each of its children add some own properties to each class of structure.
- This interface defines a group of common methods to its children:
    - contains: it's necessary to implement equals & hashCode methods so that this method works.
    - add: add an alement to the collection.
    - remove: delete an elemento from the collection.
    - isEmpty: returns true if the collection has no elements.
    - ...
# List interface
- This is an interface which extends from Collection.
- Each element of the list has an index in the list.
- Each element is added in order. This collection respect the insertion order.
- This collection allows duplicate elements.
- This interface adds its own methods, such as:
    - Get: obtain an element from its index.
    - Set: raplace the element of the index accepted as parameter.
    - ...
- There are several implementations for this interface:
    - ArrayList: an implementation that stores elements in a backing array. The array’s size will be automatically expanded if there isn’t enough room when adding new elements into the list. It’s possible to set the default size by specifying an initial capacity when creating a new ArrayList. Basically, an ArrayList offers constant time for the following operations: size, isEmpty, get, set, iterator, and listIterator; amortized constant time for the add operation; and linear time for other operations. Therefore, this implementation can be considered if we want fast, random access of the elements.
    - LinkedList: an implementation that stores elements in a doubly-linked list data structure. It offers constant time for adding and removing elements at the end of the list; and linear time for operations at other positions in the list. Therefore, we can consider using a LinkedList if fast adding and removing elements at the end of the list is required.
    - Vector: it uses a dynamic array to store the data elements. It is similar to ArrayList. However, It is synchronized and contains many methods that are not part of the Collection framework.
    - Stack: this class extends from Vector. It represents a LIFO (last in - first out) structure and hast three particular methods:
        - push: insert an element on the top of the structure.
        - pop: returns and remove the element on the top of the structure.
        - peek: returns the element on the top of the structure.
- There are samples in the code to measure the time of accessing, adding and removing of the different implementations of List interface.

# Queue interface
- It represents a FIFO (first in - first out) structure.
- This interface has the following implementations:
    - PriorityQueue: provides the facility of using queue. However, it does not order the elements in FIFO manner. It inherits AbstractQueue class.
    - LinkedList: the standard queue implementation.
- The interface Dequeue extends the interfaz Queue. It allows adding or removing elements at both ends (it has two heads). Its implementation is ArrayDeque.
- The main methods to work with a queue are:
    - add / offer: insert an element into the queue.
    - remove / poll: retrieve and remove the head of the queue.
    - element / peek: retrieve (but not remove) the head of the queue.

# Set interface
- This interface is a group of elements that has some differences with List:
    - It doesn't keep the ordering insertion.
    - It doesn't allow duplicate elements. For this, it is necessary to implement equals and hashCode methods.
- SortedSet interface extends from Set. It adds the methods comparator, first and last.
- There are some implementations (each of them behaves a little differently with respect to the order of the elements when iterating the Set, and the time it takes to insert and access elements in the sets):
    - HashSet: is the best-performing implementation and is a widely-used Set implementation. It represents the core characteristics of sets: no duplication and unordered.
    - LinkedHashSet: differs from HashSet by guaranteeing that the order of the elements during iteration is the same as the order they were inserted into the LinkedHashSet. Reinserting an element that is already in the LinkedHashSet does not change its order.
    - TreeSet: also guarantees the order of the elements when iterated, but the order is the sorting order of the elements. In other words, the order in which the elements whould be sorted if you used a Collections.sort() on a List or array containing these elements. This order is determined either by their natural order (if they implement Comparable), or by a specific Comparator implementation.

# Map interface
- It represents a key-value structure.
- It is necessary for the key to implement the equal and hashCode methods because each key must be unique.
- There are the following implementations:
    - HashMap: the most common class that implements the Map interface is the Java HashMap. A HashMap is a hash table implementation of the Map interface. It permits null keys and values. Also, this class does not maintain any order among its elements and especially, it does not guarantee that the order will remain constant over time. Finally, a HashMap contains two fundamental parameters: initial capacity and performance. The capacity is defined as the number of buckets in the hash table, while the load factor is a measure that indicates the maximum value the hash table can reach, before being automatically increased.
    - LinkedHashMap: differs from HashMap by guaranteeing that the order of the elements during iteration is the same as the order they were inserted into the LinkedHashMap.
    - TreeMap: it is a Red-Black tree implementation that is sorted according to the natural ordering of its keys, or by a Comparator provided at the creation time. Also, this class maintains an order on its elements. Finally, this class is not synchronized and thus, if an application uses multiple threads, the map must be synchronized externally.
    - ConcurrentHashMap: it is a hash table that supports full concurrency of retrievals. Thus, this structure is safe to use in case of multiple threads. Finally, this class does not allow neither keys nor values to be null.
- There is a interface which extends from Map interface: Entry interface. It provides methods to get key and value (for(Entry e : map.entrySet()){...e.getKey()...e.getValue()...})

# Graph
There is no a specific class to manage graphs in Java. However, they can be implemented in different ways. In this example, two implementations were developed:
- Adjacency Matrix: this implementation is based on a nxn matrix, being n the number of vertices. Depending on the type of graph the matrix:
  - Consists of boolean for unweighted graphs.
  - Consists of integers for weighted graphs.
- Adjacency List: this implementation is based on a Map, where each node (key) has a set of vertices connected (value):
  - In case of unweighted graphs, the key of the map is the vertex and the value, the set of nodes linked to the vertex.
  - In csae of weighted graphs, the key of the map is the vertex and the value, another set with each linked node as the key and the weight as the value.

Additionally, both weighted and unweighted maps can be both undirected or directed. In this example, there is a boolean passed in the constructor to specify whether it's directed or undirected.

There are libraries that offer a lot of graph features out of the box. One of the most common ones is [JGraphT](https://github.com/jgrapht/jgrapht/blob/master/README.md).