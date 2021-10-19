# Data Structures
A data structure is a way of organizing data so that it can be used effectively. They are essential ingredients in creating fast and powerful algorithms. They help to manage and organize data. They make code cleaner and easier to understand.

## Abstract Data Type VS Data Structure
An abstract data type (ADT) is an abstraction of a data structure which provides only the interface to which a data structure must adhere to. The interface does not give any specific details about how something should be implemented or in what programming language. Here some examples:
| ADT | DS |
|--|--|
| List | Linked List, Dynamic |
| Queue | Linked List based Queue, Array based Queue, Stack based Queue |
| Map | Tree Map, Hash Map, Hash Table |

# Static arrays
## Introduction
A static array is a fixed length container containing n elements indexable from the range [0, n - 1]. Each slot/index in the array can be referenced with a number. Furthermore, arrays are given as continuous chunk of memory.

## Big 0
| | Access | Search | Insertion | Deletion
---| ---| ---| ---| ---| 
Average | O(1) | O(N) | O(1) | O(1)
Worst | O(1) | O(N) | O(1) | O(1)

- Note: static array can't grow longer, but the action of placing a value in a specific index of the array is O(1).
- Note: static array can't get smaller, but the action of placing a null value in a specific index of the array is O(1).

## Dynamic arrays
## Introduction
Dynamic arrays are arrays which grow automatically when they are out of free slots.

## Big 0
| | Access | Search | Insertion | Deletion
---| ---| ---| ---| ---| 
Average | O(1) | O(N) | O(N) | O(N)
Worst | O(1) | O(N) | O(N) | O(N)

- Note: since the array gets bigger every time it is out of free slots, the insertion at the beginning of the array of an element would need a new and bigger array (for instance, twice as big), secondly to shift to the right all the current values, and last but not least to place the new element.
- Note: for deletion, the time complexity is as insertion, you have to shift all the elements and potentially re-copy everything into the static array used to implement the dynamic array.

# Singly Linked List
## Introduction
A linked list is a data structure which consists of a head, a tail and length properties. Linked Lists consist of nodes, and each node has a value and a pointer to the next node or null (if it is the last one). The terminology used for linked lists is:

 - Head: the first node in a linked list.
 - Tail: the last node in a linked list.
 - Pointer: reference to another node.
 - Node: the object containing data and pointer.

Let's see this example:
(head)     size=4     (tail)
  4   ->  6  ->  8  ->  2
- 4.next = 6
- 6.next = 8
- 8.next = 2
- 2.next = null

Pros of singly linked list:

 - Use less memory than doubly linked list (only 1 reference is hold).
 - Simpler implementation.

Cons of singly linked list:

 - Cannot easily access previous elements.

## Big 0
| | Access | Search | Insertion | Deletion
---| ---| ---| ---| ---| 
Average | O(N) | O(N) | O(1) | O(1)
Worst | O(N) | O(N) | O(1) | O(1)

- Note: deletion at tail of in the middle is O(N)

# Doubly Linked List
## Introduction
A doubly linked list is a data structure which consists of a head, a tail and length properties. Doubly linked Lists consist of nodes, and each node has a value and two pointers, one to the next node or null (if it is the last one), and one to the previous node (or null if it is the first one).

Let's see this example:
(head)      size=4      (tail)
  4  <->  6  <->  8  <->  2
- 4.next = 6
- 4.prev = null
- 6.next = 8
- 6.prev = 6
- 8.next = 2
- 8.prev = 6
- 2.next = null
- 2.prev = 8

Pros of singly linked list:

 - Can be traversed backward.

Cons of singly linked list:

 - Takes twice the memory.

## Big 0
| | Access | Search | Insertion | Deletion
---| ---| ---| ---| ---| 
Average | O(N) | O(N) | O(1) | O(1)
Worst | O(N) | O(N) | O(1) | O(1)

- Note: deletion at tail is O(1) and in the middle is O(N)

# Stack
## Introduction
A stack is a one-ended linear data structure which models a real world stack by having to primary operations, namely push and pop. This kind of structure is as well called a LIFO structure: Last in - First out.

## Big 0
| | Access | Search | Insertion | Deletion
---| ---| ---| ---| ---| 
Average | O(N) | O(N) | O(1) | O(1)
Worst | O(N) | O(N) | O(1) | O(1)

# Queue
## Introduction
A queue is a linear data structure which models a real world queues by having to primary operations, namely enqueue and dequeue. This kind of structure is as well called a FIFO structure: First in - First out.

## Big 0
| | Access | Search | Insertion | Deletion
---| ---| ---| ---| ---| 
Average | O(N) | O(N) | O(1) | O(1)
Worst | O(N) | O(N) | O(1) | O(1)

- Note: deletion in the middle is O(N)

# Priority Queues
## Introduction
A priority queue (PQ) is an abstract data type (ADT) that operates similar to a normal queue except that each element has a certain priority. The priority of the elements in the priority queue determine the order in which elements are removed from the PQ. PQ are implemented with Binary Heaps. Basically, element with higher priorities are served before elements with lower priorities.

Priority queues only support comparable data because the data inserted into the queue must be able to be ordered in some way either from the least to the greatest or vice versa. The queue needs that to be able to assign relative priorities to each element.

Let's assume we've got the following numbers in our queue: 14, 4, 1, 3, 8, 22. We're going to run some operations with an ordering imposed on the numbers to be from least to greatest.

 1. Poll() (remove) -> Return 1 (the one with the highest priority).
 2. Add(2).
 3. Poll() -> Return 2 (the one just added).
 4. Add(4).
 5. Poll() -> Return 3.
 6. Add(5).
 8. Add(9).
 9. Poll rest -> Return 4, 4, 5, 8, 9, 14, 22.

## Big 0
| | Access | Search | Insertion | Deletion
---| ---| ---| ---| ---| 
Average | O(N) | O(N) | O(log N) | O(log N)
Worst | O(N) | O(N) | O(log N) | O(log N)

# Binary Search Tree
## Introduction to trees
A tree is a data structure that consists of nodes in a parent / child relationship. The terminology used for trees is:
                                                                                    
- Root: the top node in the tree.
- Child: a node connected directly to another node as a child.
- Parent: the converse notion of a child.
- Siblings: a group of nodes with the same parent.
- Leaf: node with no children.
- Edge: the connection between one node and another (the arrow).

Trees are used for:
- HTML DOM.
- Networking route.
- Abstract syntax tree.
- Artificial intelligence.
- Folders in operating systems.
- Computer file systems.

## Introduction to binary trees
A binary tree is just a tree with the following properties:
- Each node has at most two children (0, 1 or 2).

## Introduction to binary search trees
A binary search tree is just a binary tree with the following properties:
- Every node to the left of a parent node is always less than the parent.
- Every node to the right of a parent node is always greater than the parent.

## Big 0
| | Access | Search | Insertion | Deletion
---| ---| ---| ---| ---| 
Average | O(log N) | O(log N) | O(log N) | O(log N)
Worst | O(log N) | O(log N) | O(log N) | O(log N)

## Tree Traversal
There is no standard way to iterate a tree. For instance, a list is from the left to the right, but a tree can be from the left to the right by levels, of deeply until the end of each branch. We'll explain four ways of iterating an array:
- Breadth first. Iterate per level, from left to right.
- Depth first preorder. Iterate per branch, from the root until the end, from left to right. We visit the node before visiting the left and the rifht.
- Depth first postorder. The opposite of preorder, we visit a node after having visited the left and the right.
- Depth first inorder. Mix between pre and postorder: we visit the left side, then visit the node, and then visit the right side.

Let's assume the next tree:
```
              10
            /    \
           6      15
          /  \     \
         3    8     20
```
- Breadth first: 10, 6, 15, 3, 8, 20
- Depth first preorder: 10, 6, 3, 8, 15, 20
- Depth first postorder: 3, 8, 6, 20, 15, 10.
- Depth first inorder: 3, 6, 8, 10, 15, 20.

When to use BFS (Breadth First Search) and DFS (Depth First Animal)?
- Space complexity: if it is a really wide tree, BFS could take a lot of more space than DFS. If it is a really deep, long tree, DFS could take a lot of more space than BFS.
- Time complexity is the same for both.
- DFS Pre-Order: can be used to "export" a tree structure so that it is easily reconstructed or copied. You get the root, then get the left, then get the right, ...
- DFS In-Order: used commonly with Binary Search Trees (elements are iterated in order, from the smallest to the largest).

# Binary Heaps
## Introduction
Heaps are another category of trees, so everything which applies to trees in general, applies to heaps.

A Binary Heap is very similar to a Binary Search Tree but with some different rules:
- In a MaxBinaryHeap, parent nodes are always larger than child nodes, both left and right children.
- In a MinBinaryHeap, parent nodes are always smaller than child nodes, both left and right children.
- Unlike a Binary Search Tree, where left child is smaller than the node and right child is larger, that rule doesn't apply in Binary Heap. Hence, there is no order stablished.

This is an example of MaxBinaryHeap:
```
              41
            /    \
           39     33
          /  \   / 
         18  27 12
```

Max Binary Heap properties:
1. Each parent has at most two children nodes.
2. The value of each parent is always bigger greater than its child nodes.
3. Even though the parent is always greater than the children, there are no guarantees between sibling nodes.
4 It is as compact as possible. All the children of each node are as full as they can be and left children are filled out first. For instance, in a Binary Search Tree, if we have a serie of numbers each one greater than the previous one, the tree is built like a list, just with right nodes. But this is not the case for Binary Heap, they are built as compact as possible to try to minimize the depth of the tree.

Let's see this example where we're inserting the following values: 100, 19, 36, 17, 3, 25, 1, 2, 7
1. 100 is the root.
2. 19 is smaller than 100, so it's ok. We fill out left first, so the left child of 100 is 19.
3. 36 is smaller than 100, but bigger than 19, so it can't not be child of 19. Hence, 36 is the right child of 100.
4. 17 is smaller than 100, it's smaller than 19 and smaller than 36. We fill out left first, so 17 is left child of 19.
5. 3 is smaller than 100, 19, 36 and 17. 3 can be child of 19 and 17, but we want the heap to be as compact as possible, so 3 becomes the right child of 19.
6. 25 is smaller than 100, not smaller than 19 (so it can go to the left branch), and it's smaller than 36. We fill out left first, so 25 is left child of 36.
7. 1 is smaller than all of the values in the tree, but we want the heap to be as compact as possible, and there is a free gap as right child of 36.
8. Now the tree is full (all nodes have two children except the leafs). 2 is smaller than 17, 3 and 25, but since we fill out left first, 2 become left child of 17.
9. 7 is smaller than 17 and 25, but we fill out left first, and 17 is in the left branch of the root. So 2 becomes right child of 17.

```
                  100
             /            \
           19             36
        /      \      /        \
       17      3     25         1
     /   \
    2     7
```

As we can see, there is no order between siblings (see 17, 3, 25, 1), which is the 3rd property of the binary heaps.

Binary heaps are used to implement priority queues. They are also used quite a bit, with graph traversal algorithms.

## Store a binary heap
Even though a binary heap can be built in the same way than a binary search tree (a node class, with a left and a right nodes, ...), there is an easy way to do it: array/list. The previous example could be stored like this: [100, 19, 36, 17, 3, 25, 1, 2, 7].

How can we iterate the array? For instance, if we want to find children of 100, it's easy, two next positions: 19 and 36. But if we want to find children of 19. We need to skip 36 and go to 17 and 3. What about children of 17? We need to skip 3, 25 and 1, and go to 2 and 7. Basically the position of the element in the array tells you how many elements to skip:
- 100: it is in the index 0. It means, to skip 0 elements, so its children will be the 2 next ones.
- 19: it is in the index 1. It means to skip 1 element (36), so its children will be the 2 next ones to the last element skipped (2 next ones to 36).
- 17: it is in the index 3. It means to skip 3 elements (3, 25 and 1) so its children will be the 2 next ones to the last element skipped (2 next ones to 1).
- 36: it is in the index 2. It means, to skip 2 elements (17 and 3), so its children will be the 2 next ones to the last element skipped (2 next ones to 3).

Basically, for each element in index n, the left child will be located in 2n + 1 and the right one in 2n + 2.
What if we have a child node and we want its parent? If we have an element in index n, its parent will be in floor((n - 1) / 2).

## Implementation
To implement a Max Binary Heap we only need an array or list. To insert a new element we need to:
1. Add at the end of the array.
2. Bubble up having in mind the rules of the Binary Heap. Basically, we take the node and compare it to its parent (located in floor(n - 1 / 2). If the new value is larger, swap elements. Keep doing that until the new value either is the first element of the array of it is not greater than its parent.

The element removed from the Max/Min Binary Heap will be always the root, since it's the top/bottom priority (greater/smaller) element. Basically the process consists of:
1. Remove the root.
2. Replace the root with the most recently added, the last element of the array.
3. Adjust. Basically, we compare the new root with its children, and we swap it with the greatest child. We do that until the last element is not greater than any of the new children or it is a leaf.

## Big 0
| | Access | Search | Insertion | Deletion
---| ---| ---| ---| ---| 
Average | O(1) | O(N) | O(log N) | O(log N)
Worst | O(1) | O(N) | O(log N) | O(log N)

- Note: to insert, we put the element at the end and we bubble it up, with a maximum of logN bubbles.
- Note: same for deletion, but starting from the root.
- Note: access is just to the root of the tree, so it's O(1).

# Hash Tables
## Introduction
Hash tables are used to store key-value pairs:
- They are like arrays or lists, but the keys are not ordered.
- Unlike arrays, where keys are numbers, key can be anything.
- Unlike arrays, they are fast for the following operations: finding values, adding new values and removing values.

Because of their speed, hash tables are commonly used.

For the hash tables to work, we need a way to convert the key (which can be literally anything, any data type) into a valid index. The function which perform this tasks is called hash function.

## Hash Functions
Conceptually a hash function is a function which takes a key and returns a specific number linked to that key. The key for the hash function to work is that the same key must return always the same index, and an index should be returned only by one concrete key. This last part doesn't happen always, and when it happens we call it collision, and the hash table must have a way to handle collisions.

This way, every time I pass a key, the hash function will return the index immediately and we'll get the data by using that key (O(1)).

A basic hash function is a function which takes data of arbitrary size and spit out data of a fixed size (the array length).

What makes a good hash function?
1. Fast (i.e. O(1))).
2. Doesn't cluster outputs at specific indexes, but distributes uniformly.
3. Deterministic: same input results in the same output.

Hash functions always take advantage of prime numbers to avoid collisions and to distribute the data uniformly.

## Collisions
Even with a large array and a great hash function, collisions are inevitable. How do we handle them? There are a lot of different ways, but we'll focus on two:
- Separate chaining: at each index in our array we store values using a more sophisticated data structure (e.g. an array or linked list). This allows us to store multiple key-value pairs at the same index.
- Linear probing: when we find a collision, we search through the array to find the next empty slot. Unlike separate chaining, this allows us to store a single key-value at each index. 

## Big 0
| | Access | Search | Insertion | Deletion
---| ---| ---| ---| ---| 
Average | O(1) | N/A | O(1) | O(1)
Worst | O(N) | N/A | O(N) | O(N)

- Note: average scenario is when we've got a good hash function which distributes the elements through the array.
- Note: worst case scenario is when we've got the worst hash function ever and the hash function returns the same hash value for all the elements.

# Graphs
## Introduction
A graph is a collection of nodes and connection. One node can connect to 0 or more nodes, and one node can be connected by 1 or more nodes.

Graphs are used for instance for:
- Social networking.
- Location / mapping.
- Routing algorithms.
- Visual hierarchy.
- File system optimization.
- Recommendations (Netflix, Hulu, Amazon, ...).
- ...

The properties of a graph are:
- Vertex: a node.
- Edge: connection between nodes. There are two different of edges:
  - Weighted / unweighted: the connection between the vertices has a velue or weight (or not). Example of weighted is a map, where values (distance) are used to calculate the min path between nodes.
  - Directed / undirected: the connection can be iterated only in a specific direction (or not). Example of undirected is a graph of connections. Example of directed is a graph of followers (who is following who), or a map for roads of one way direction.

## Storing graphs
These are two ways of storing graphs:
- Adjacency matrix: basically this is a matrix where both columns and rows are the elements of the graph, and the values of the matrix is 0 if there is no connection between the node for that position and 1 or weight if there is. This matrix is always symmetrical for undirected graphs.
- Adjacency list: basically this is a list of lists where each list is the connection between nodes. For instance: [[1, 5], [0, 2], [1, 3], [2, 4], [3, 5]]. We can see there is an edge between the nodes 1 and 5, and so on. A hash table can be used as well, where the key is the node and the values the nodes liked to it: {"A": ["B", "C"], "B": ["A", "C"], "C": ["A", "B"]}. We can use an object to give a weight to the edge, instead of "B", {"B", 5}.

## Big 0
Big O depends on the implementation:

| | Add Vertex | Add Edge | Remove Vertex | Remove Edge | Query | Storage
---| ---| ---| ---| ---| ---| ---|
Adjacency list | O(1) | O(1) | O(V + E) | O(E) | O(V + E) | O(V + E)
Adjacency matrix | O(V^2) | O(1) | O(V^2) | O(1) | O(1) | O(V^2)

Adjacency list:
- Can take up less space.
- Faster to iterate over all edges.
- Can be slower to look up specific edge.

Adjacency matrix:
- Takes up more space.
- Slower to iterate over all edges.
- Faster to lookup specific edge.

Overall, always use adjacency list, mainly because it takes less space and the data in the real world tend to be sparser 

## Graph Traversal
Let's see this graph:
```
    5 <--- 1 <--- 0 ---> 2
           |----> 6
```
There are two different ways of iterating a graph:
1. Depth first (DFS): explore as far as possible down one branch before backtracking. This basically means to pick one node, to pick one node of that node, ..., until reaching the end. It is kind of going from the top to the bottom of a tree. In the previous example, if we pick 0 to start, this would be the result: [0, 1, 5, 6, 2]
2. Breadth first (BFS): explore all nodes of the current node before going further. In the previous example, if we pick 0 to start, this would be the result: [0, 1, 2, 5, 6]