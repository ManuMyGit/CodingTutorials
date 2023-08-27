package org.mjjaen.java9.collection;

import lombok.extern.slf4j.Slf4j;
import org.mjjaen.java9.Example;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
@Slf4j
public class CollectionExample implements Example {
    @Override
    public void runExample() {
        log.info("Running collection new methods example ...");
        log.info("Creating and printing a list");
        List<Integer> myList = List.of(1, 2, 3, 4, 5);
        log.info(myList.toString());
        log.info("Creating and printing a set");
        Set<Integer> mySet = Set.of(1, 2, 3, 4, 5);
        log.info(mySet.toString());
        log.info("Creating and printing a map");
        Map<String, Integer> myMap = Map.of("Key1", 1, "key2", 2, "key3", 3, "key4", 4, "key5", 5);
        log.info(myMap.toString());
    }
}
