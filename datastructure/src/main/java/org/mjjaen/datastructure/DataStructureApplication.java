package org.mjjaen.datastructure;

import org.mjjaen.datastructure.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.*;

@SpringBootApplication
public class DataStructureApplication implements CommandLineRunner
{
    @Autowired
    private ListFactory listFactory;

    @Autowired
    private ServiceList serviceList;

    @Autowired
    private ListConfiguration listConfiguration;

    public static void main(String[] args) {
        SpringApplication.run(DataStructureApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        this.listExamples();
        this.queueExamples();
        this.setExamples();
        this.mapExamples();
    }

    private void listExamples() {
        //Two different implementations of list are created
        List<Integer> list = listFactory.createList(ListFactory.ARRAYLIST);
        List<Integer> list2 = listFactory.createList(ListFactory.LINKEDLIST);
        List<Integer> list3 = listFactory.createList(ListFactory.ARRAYLIST);
        List<Integer> list4 = listFactory.createList(ListFactory.LINKEDLIST);

        //Both lists are initializated by adding 10000 numbers always at the end of the list.
        System.out.print("Time to add (O(1)) " + listConfiguration.getSize() + " elements to the ArrayList implementation: ");
        serviceList.initializeList(list);
        serviceList.initializeList(list3);
        System.out.print("Time to add (O(1)) " + listConfiguration.getSize() + " elements to the LinkedList implementation: ");
        serviceList.initializeList(list2);
        serviceList.initializeList(list4);

        System.out.print("Time to get (O(1)) the element in the position " + listConfiguration.getSize() / 2 +  " to the ArrayList implementation: ");
        serviceList.getElement(list, listConfiguration.getSize() / 2);
        System.out.print("Time to get (O(n/4)) the element in the position " + listConfiguration.getSize() / 2 +  " to the LinkedList implementation: ");
        serviceList.getElement(list2, listConfiguration.getSize() / 2);

        System.out.print("Time to remove (O(n/2)) all the elements of the list through an iterator to the ArrayList implementation: ");
        serviceList.removeElementsFromListWithIterator(list);
        System.out.print("Time to remove (O(1)) all the elements of the list through an iterator to the LinkedList implementation: ");
        serviceList.removeElementsFromListWithIterator(list2);

        System.out.print("Time to remove (O(n/2)) all the elements of the list to the ArrayList implementation: ");
        serviceList.removeElementsFromList(list3);
        System.out.print("Time to remove (O(n/4)) all the elements of the list to the LinkedList implementation: ");
        serviceList.removeElementsFromList(list4);

        System.out.print("Time to add (O(n/2)) " + listConfiguration.getElementsAtTheMiddle() + " elements at the middle of the list to the ArrayList implementation: ");
        serviceList.addElementsToTheList(list);
        System.out.print("Time to add (O(n/4)) " + listConfiguration.getElementsAtTheMiddle() + " elements at the middle of the list to the LinkedList implementation: ");
        serviceList.addElementsToTheList(list2);

        System.out.println("\nOperations with Stack");
        Stack<Integer> stack = new Stack<Integer>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        System.out.println("Stack: " + stack);
        stack.peek();
        System.out.println("Stack after using peek: " + stack);
        stack.pop();
        System.out.println("Stack after using pop: " + stack);
    }

    private void queueExamples() {
        System.out.println("\nOperations with Queue");
        Queue<Integer> queue = new LinkedList<Integer>();
        queue.add(1);
        queue.add(4);
        queue.add(3);
        queue.add(2);
        System.out.println("LinkedList implementation of Queue (order of adding: 1, 4, 3, 2): " + queue);
        Queue<Integer> priorityQqueue = new PriorityQueue<Integer>();
        priorityQqueue.add(1);
        priorityQqueue.add(4);
        priorityQqueue.add(3);
        priorityQqueue.add(2);
        System.out.println("PriorityQueue implementation of Queue (order of adding: 1, 4, 3, 2): " + priorityQqueue);
        queue.element();
        System.out.println("Queue after using element: " + queue);
        queue.peek();
        System.out.println("Queue after using peek: " + queue);
        queue.remove();
        System.out.println("Queue after using remove: " + queue);
        queue.poll();
        System.out.println("Queue after using poll: " + queue);

        Deque<Integer> deque = new ArrayDeque<Integer>();
        deque.addLast(1);
        deque.addLast(2);
        deque.addLast(3);
        deque.addFirst(4);
        deque.addFirst(5);
        deque.addFirst(6);
        System.out.println("Deque (order of adding: last 1, last 2, last 3, first 4, first 5, first 6): " + deque);
        deque.element();
        System.out.println("Deque after using element: " + deque + ". Result of deque.element(): " + deque.element());
        deque.peek();
        System.out.println("Deque after using peek: " + deque + ". Result of deque.peek(): " + deque.peek());
        deque.remove();
        System.out.println("Deque after using remove (same than removeFirst): " + deque);
        deque.poll();
        System.out.println("Deque after using poll (same than pollFirst): " + deque);
        deque.removeLast();
        System.out.println("Deque after using removeLast: " + deque);
        deque.pollLast();
        System.out.println("Deque after using pollLast: " + deque);
    }

    private void setExamples() {
        Set<MyInteger> hashSet = new HashSet<MyInteger>();
        Set<MyInteger2> hashSet2 = new HashSet<MyInteger2>();
        hashSet.add(new MyInteger(1));
        hashSet.add(new MyInteger(4));
        hashSet.add(new MyInteger(3));
        hashSet.add(new MyInteger(2));
        hashSet.add(new MyInteger(1));
        hashSet.add(new MyInteger(1));
        hashSet.add(new MyInteger(1));
        hashSet2.add(new MyInteger2(1));
        hashSet2.add(new MyInteger2(2));
        hashSet2.add(new MyInteger2(3));
        hashSet2.add(new MyInteger2(4));
        hashSet2.add(new MyInteger2(1));
        hashSet2.add(new MyInteger2(1));
        hashSet2.add(new MyInteger2(1));
        System.out.println("Class MyInteger and MyInteger2 just with one attribute (Integer), one with equals and hashCode and the otner one without it");
        System.out.println("HashSet after adding 1, 4, 3, 2, 1, 1 and 1 (in that order) with a class with equals and hashCode: " + hashSet);
        System.out.println("HashSet after adding 1, 2, 3, 4, 1, 1 and 1 (in that order) with a class without equals and hashCode: " + hashSet2);
        Set<Integer> linkedHashSet = new LinkedHashSet<Integer>();
        linkedHashSet.add(1);
        linkedHashSet.add(4);
        linkedHashSet.add(3);
        linkedHashSet.add(2);
        linkedHashSet.add(1);
        linkedHashSet.add(1);
        linkedHashSet.add(1);
        System.out.println("LinkedHashSet after adding 1, 4, 3, 2, 1, 1 and 1 (in that order): " + linkedHashSet);
        Set<Integer> treeSet = new TreeSet<Integer>();
        treeSet.add(1);
        treeSet.add(4);
        treeSet.add(3);
        treeSet.add(2);
        treeSet.add(1);
        treeSet.add(1);
        treeSet.add(1);
        System.out.println("TreeSet after adding 1, 4, 3, 2, 1, 1 and 1 (in that order): " + treeSet);
    }

    public void mapExamples() {
        Map<Integer, Integer> hashMap = new HashMap<Integer, Integer>();
        hashMap.put(1, 2);
        hashMap.put(4, 8);
        hashMap.put(3, 6);
        hashMap.put(2, 4);
        hashMap.put(1, 2);
        hashMap.put(1, 2);
        hashMap.put(1, 2);
        System.out.println("HashMap after adding 1:2, 4:8, 3:6, 2:4, 1:2, 1:2 and 1:2 (in that order): " + hashMap);
        System.out.println("hashMap.get(1): " + hashMap.get(1));
        System.out.println("hashMap.get(5): " + hashMap.get(5));
        System.out.println("hashMap.keySet(): " + hashMap.keySet());
        System.out.println("hashMap.values(): " + hashMap.values());
        System.out.println("Iterate over hashMap.entrySet()");
        for(Map.Entry<Integer, Integer> entry : hashMap.entrySet()) {
            System.out.println("     Entry.getKey(): " + entry.getKey());
            System.out.println("     Entry.getValue(): " + entry.getValue());
        }
        System.out.println("hashMap.remove(1): " + hashMap.remove(1));
        System.out.println("hashMap.remove(5): " + hashMap.remove(5));
        System.out.println("HashMap after removing: " + hashMap);
        Map<Integer, Integer> linkedHashMap = new LinkedHashMap<Integer, Integer>();
        linkedHashMap.put(1, 1);
        linkedHashMap.put(4, 4);
        linkedHashMap.put(3, 3);
        linkedHashMap.put(2, 2);
        linkedHashMap.put(1, 1);
        linkedHashMap.put(1, 1);
        linkedHashMap.put(1, 1);
        System.out.println("LinkedHashMap after adding 1:1, 4:4, 3:3, 2:2, 1:1, 1:1 and 1:1 (in that order): " + linkedHashMap);
        Map<Integer, Integer> treeMap = new TreeMap<Integer, Integer>();
        treeMap.put(1, 1);
        treeMap.put(4, 4);
        treeMap.put(3, 3);
        treeMap.put(2, 2);
        treeMap.put(1, 1);
        treeMap.put(1, 1);
        treeMap.put(1, 1);
        System.out.println("TreeMap after adding 1:1, 4:4, 3:3, 2:2, 1:1, 1:1 and 1:1 (in that order): " + treeMap);
    }

    public ListFactory getListFactory() {
        return listFactory;
    }

    public void setListFactory(ListFactory listFactory) {
        this.listFactory = listFactory;
    }

    public ServiceList getServiceList() {
        return serviceList;
    }

    public void setServiceList(ServiceList serviceList) {
        this.serviceList = serviceList;
    }
}
