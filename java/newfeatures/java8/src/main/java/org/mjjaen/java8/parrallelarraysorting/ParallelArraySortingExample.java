package org.mjjaen.java8.parrallelarraysorting;

import lombok.extern.slf4j.Slf4j;
import org.mjjaen.java8.Example;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Slf4j
public class ParallelArraySortingExample implements Example {
    @Override
    public void runExample() {
        int[] numbers = { 9, 8, 7, 6, 3, 1 };

        // Printing unsorted Array
        log.info("Unsorted Array: ");
        // Iterating the Elements using stream
        Arrays.stream(numbers).forEach(n -> log.info(n + " "));

        // Using Arrays.parallelSort()
        Arrays.parallelSort(numbers);

        // Printing sorted Array
        log.info("Sorted Array: ");
        // Iterating the Elements using stream
        Arrays.stream(numbers).forEach(n -> log.info(n + " "));
    }
}
