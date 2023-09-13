package org.mjjaen.datastructure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mjjaen.datastructure.graphs.*;
import org.mjjaen.datastructure.graphs.impl.*;
import org.mjjaen.datastructure.utils.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.*;

@SpringBootApplication
@RequiredArgsConstructor
@Slf4j
public class DataStructureApplication implements CommandLineRunner
{
    private final ListFactory listFactory;

    private final ServiceList serviceList;

    private final ListConfiguration listConfiguration;

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
        log.info("Time to add (O(1)) " + listConfiguration.getSize() + " elements to the ArrayList implementation: ");
        serviceList.initializeList(list);
        serviceList.initializeList(list3);
        log.info("Time to add (O(1)) " + listConfiguration.getSize() + " elements to the LinkedList implementation: ");
        serviceList.initializeList(list2);
        serviceList.initializeList(list4);

        log.info("Time to get (O(1)) the element in the position " + listConfiguration.getSize() / 2 +  " to the ArrayList implementation: ");
        serviceList.getElement(list, listConfiguration.getSize() / 2);
        log.info("Time to get (O(n/4)) the element in the position " + listConfiguration.getSize() / 2 +  " to the LinkedList implementation: ");
        serviceList.getElement(list2, listConfiguration.getSize() / 2);

        log.info("Time to remove (O(n/2)) all the elements of the list through an iterator to the ArrayList implementation: ");
        serviceList.removeElementsFromListWithIterator(list);
        log.info("Time to remove (O(1)) all the elements of the list through an iterator to the LinkedList implementation: ");
        serviceList.removeElementsFromListWithIterator(list2);

        log.info("Time to remove (O(n/2)) all the elements of the list to the ArrayList implementation: ");
        serviceList.removeElementsFromList(list3);
        log.info("Time to remove (O(n/4)) all the elements of the list to the LinkedList implementation: ");
        serviceList.removeElementsFromList(list4);

        log.info("Time to add (O(n/2)) " + listConfiguration.getElementsAtTheMiddle() + " elements at the middle of the list to the ArrayList implementation: ");
        serviceList.addElementsToTheList(list);
        log.info("Time to add (O(n/4)) " + listConfiguration.getElementsAtTheMiddle() + " elements at the middle of the list to the LinkedList implementation: ");
        serviceList.addElementsToTheList(list2);

        log.info("\nOperations with Stack");
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        log.info("Stack: " + stack);
        stack.peek();
        log.info("Stack after using peek: " + stack);
        stack.pop();
        log.info("Stack after using pop: " + stack);
    }

    private void queueExamples() {
        log.info("\nOperations with Queue");
        Queue<Integer> queue = new LinkedList<>();
        queue.add(1);
        queue.add(4);
        queue.add(3);
        queue.add(2);
        log.info("LinkedList implementation of Queue (order of adding: 1, 4, 3, 2): " + queue);
        Queue<Integer> priorityQqueue = new PriorityQueue<>();
        priorityQqueue.add(1);
        priorityQqueue.add(4);
        priorityQqueue.add(3);
        priorityQqueue.add(2);
        log.info("PriorityQueue implementation of Queue (order of adding: 1, 4, 3, 2): " + priorityQqueue);
        queue.element();
        log.info("Queue after using element: " + queue);
        queue.peek();
        log.info("Queue after using peek: " + queue);
        queue.remove();
        log.info("Queue after using remove: " + queue);
        queue.poll();
        log.info("Queue after using poll: " + queue);

        Deque<Integer> deque = new ArrayDeque<>();
        deque.addLast(1);
        deque.addLast(2);
        deque.addLast(3);
        deque.addFirst(4);
        deque.addFirst(5);
        deque.addFirst(6);
        log.info("Deque (order of adding: last 1, last 2, last 3, first 4, first 5, first 6): " + deque);
        deque.element();
        log.info("Deque after using element: " + deque + ". Result of deque.element(): " + deque.element());
        deque.peek();
        log.info("Deque after using peek: " + deque + ". Result of deque.peek(): " + deque.peek());
        deque.remove();
        log.info("Deque after using remove (same than removeFirst): " + deque);
        deque.poll();
        log.info("Deque after using poll (same than pollFirst): " + deque);
        deque.removeLast();
        log.info("Deque after using removeLast: " + deque);
        deque.pollLast();
        log.info("Deque after using pollLast: " + deque);
    }

    private void setExamples() {
        Set<MyInteger> hashSet = new HashSet<>();
        Set<MyInteger2> hashSet2 = new HashSet<>();
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
        log.info("Class MyInteger and MyInteger2 just with one attribute (Integer), one with equals and hashCode and the otner one without it");
        log.info("HashSet after adding 1, 4, 3, 2, 1, 1 and 1 (in that order) with a class with equals and hashCode: " + hashSet);
        log.info("HashSet after adding 1, 2, 3, 4, 1, 1 and 1 (in that order) with a class without equals and hashCode: " + hashSet2);
        Set<Integer> linkedHashSet = new LinkedHashSet<>();
        linkedHashSet.add(1);
        linkedHashSet.add(4);
        linkedHashSet.add(3);
        linkedHashSet.add(2);
        linkedHashSet.add(1);
        linkedHashSet.add(1);
        linkedHashSet.add(1);
        log.info("LinkedHashSet after adding 1, 4, 3, 2, 1, 1 and 1 (in that order): " + linkedHashSet);
        Set<Integer> treeSet = new TreeSet<>();
        treeSet.add(1);
        treeSet.add(4);
        treeSet.add(3);
        treeSet.add(2);
        treeSet.add(1);
        treeSet.add(1);
        treeSet.add(1);
        log.info("TreeSet after adding 1, 4, 3, 2, 1, 1 and 1 (in that order): " + treeSet);
    }

    public void mapExamples() {
        Map<Integer, Integer> hashMap = new HashMap<>();
        hashMap.put(1, 2);
        hashMap.put(4, 8);
        hashMap.put(3, 6);
        hashMap.put(2, 4);
        hashMap.put(1, 2);
        hashMap.put(1, 2);
        hashMap.put(1, 2);
        log.info("HashMap after adding 1:2, 4:8, 3:6, 2:4, 1:2, 1:2 and 1:2 (in that order): " + hashMap);
        log.info("hashMap.get(1): " + hashMap.get(1));
        log.info("hashMap.get(5): " + hashMap.get(5));
        log.info("hashMap.keySet(): " + hashMap.keySet());
        log.info("hashMap.values(): " + hashMap.values());
        log.info("Iterate over hashMap.entrySet()");
        for(Map.Entry<Integer, Integer> entry : hashMap.entrySet()) {
            log.info("     Entry.getKey(): " + entry.getKey());
            log.info("     Entry.getValue(): " + entry.getValue());
        }
        log.info("hashMap.remove(1): " + hashMap.remove(1));
        log.info("hashMap.remove(5): " + hashMap.remove(5));
        log.info("HashMap after removing: " + hashMap);
        Map<Integer, Integer> linkedHashMap = new LinkedHashMap<>();
        linkedHashMap.put(1, 1);
        linkedHashMap.put(4, 4);
        linkedHashMap.put(3, 3);
        linkedHashMap.put(2, 2);
        linkedHashMap.put(1, 1);
        linkedHashMap.put(1, 1);
        linkedHashMap.put(1, 1);
        log.info("LinkedHashMap after adding 1:1, 4:4, 3:3, 2:2, 1:1, 1:1 and 1:1 (in that order): " + linkedHashMap);
        Map<Integer, Integer> treeMap = new TreeMap<>();
        treeMap.put(1, 1);
        treeMap.put(4, 4);
        treeMap.put(3, 3);
        treeMap.put(2, 2);
        treeMap.put(1, 1);
        treeMap.put(1, 1);
        treeMap.put(1, 1);
        log.info("TreeMap after adding 1:1, 4:4, 3:3, 2:2, 1:1, 1:1 and 1:1 (in that order): " + treeMap);
    }
}