package org.mjjaen.java8.functionalinterfaces;

import lombok.extern.slf4j.Slf4j;
import org.mjjaen.java8.Example;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.function.*;

@Component
@Slf4j
public class FunctionalInterfacesExample implements Example {
    @Override
    public void runExample() {
        log.info("Testing functional interface used to create a lambda expression ...");
        MyFunctionalInterface lambdaExample = () -> log.info("");
        lambdaExample.performAction();
        log.info("Testing functional interface used to create a method reference ...");
        List<String> messages = Arrays.asList("hello", "baeldung", "readers!");
        messages.forEach(System.out::println);
        log.info("Testing functional interface Consumer<T> ...");
        Consumer<String> consumer = (x) -> log.info(x);
        consumer.accept("This is a test of consumer");
        log.info("Testing functional interface BiConsumer<T, U> ...");
        BiConsumer<String, Integer> biConsumer = (x, y) -> log.info("This is the first input: " + x + " and this is the second input: " + y);
        biConsumer.accept("This is a test of bi consumer", 3);
        log.info("Testing functional interface Supplier<T> ...");
        Supplier<String> supplier = () -> new String("This is an example of supplier, a function that gets no argument and returns an object");
        log.info(supplier.get());
        log.info("Testing functional interface Predicate<T> ...");
        Predicate<String> predicate = (x) -> x.length() == 5;
        log.info(String.valueOf(predicate.test("Example")));
        log.info(String.valueOf(predicate.test("Today")));
        List<String> list = Arrays.asList("Example", "Today");
        list.stream().filter(predicate).forEach(x -> log.info(x));
        log.info("Testing functional interface Function<T, R> ...");
        Function<String, String> function = (x) -> x.toUpperCase();
        log.info(String.valueOf(predicate.test("Example")));
        log.info(String.valueOf(predicate.test("Today")));
        list.stream().map(function).forEach(x -> log.info(x));
        log.info("Testing higher functions ");
        List<Integer> list2 = Arrays.asList(1, 2, 3, 4, 5);
        Consumer<Integer> consumer2 = x -> log.info(x.toString());
        forEach(list2, consumer2);
        forEach(list2, x -> log.info(x.toString()));
    }

    private <T> void forEach(List<T> list, Consumer<T> consumer) {
        for (T t : list) {
            consumer.accept(t);
        }
    }
}
