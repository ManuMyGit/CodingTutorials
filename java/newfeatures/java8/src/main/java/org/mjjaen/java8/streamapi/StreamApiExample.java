package org.mjjaen.java8.streamapi;

import lombok.extern.slf4j.Slf4j;
import org.mjjaen.java8.Example;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@Slf4j
public class StreamApiExample implements Example {
    @Override
    public void runExample() {
        log.info("Testing stream creation methods ...");
        Stream<Integer> stream1 = Stream.of(1, 3, 5, 7, 9, 2, 4, 6, 8, 10);
        Stream<Integer> stream2 = Stream.of(11);
        Stream<Integer> stream3 = Stream.empty();
        Integer[] array = {12, 12, 13, 14, 14, 15, 16, 17, 18, 19, 20, 21};
        Stream<Integer> stream4 = Stream.of(array);
        Stream<Integer> stream5 = Arrays.asList(21, 22, 23, 24, 25, 26, 27, 28, 29, 30).stream();
        Stream<Integer> stream6 = Stream.generate(new Random()::nextInt).limit(10);
        Stream<Integer> stream7 = Stream.<Integer>builder().add(31).add(32).add(33).add(34).add(35).add(36).add(37).add(38).add(39).add(40).build();
        Stream<Integer> stream8 = Stream.concat(stream1, stream2);
        Stream<int[]> stream9 = Stream.iterate(new int[]{41, 42}, n -> new int[]{n[0] + 2, n[1] + 2}).limit(5);
        List<Integer> list1 = Arrays.asList(51, 52, 53);
        List<Integer> list2 = Arrays.asList(54, 55, 56);
        List<Integer> list3 = Arrays.asList(57, 58, 59);
        Stream<List<Integer>> stream10 = Arrays.asList(list1, list2, list3).stream();

        log.info("Testing stream iteration methods ...");
        stream1 = Stream.of(1, 3, 5, 7, 9, 2, 4, 6, 8, 10);
        stream1.forEach(e -> log.info(e.toString()));
        stream1 = Stream.of(1, 3, 5, 7, 9, 2, 4, 6, 8, 10);
        stream1.forEachOrdered(e -> log.info(e.toString()));

        log.info("Testing filter methods ...");
        stream4.filter(e -> e % 2 == 0).distinct().forEach(e -> log.info(e.toString()));
        stream5.skip(2).limit(5).forEach(e -> log.info(e.toString()));

        log.info("Testing order methods ...");
        stream1 = Stream.of(1, 3, 5, 7, 9, 2, 4, 6, 8, 10);
        stream1.sorted().forEach(e -> log.info(e.toString()));
        stream1 = Stream.of(1, 3, 5, 7, 9, 2, 4, 6, 8, 10);
        stream1.sorted(Comparator.comparingInt(x -> x)).forEach(e -> log.info(e.toString()));

        log.info("Testing mapping methods ...");
        stream6.map(n -> {
            log.info(n.toString());
            return n / 1000;
        }).forEach(e -> log.info(e.toString()));
        stream10.flatMap(list -> list.stream()).forEach(e -> log.info(e.toString()));

        log.info("Testing reduction methods ...");
        log.info(Long.valueOf(stream3.count()).toString());
        log.info(stream7.max((x, y) -> x.compareTo(y)).get().toString());
        log.info(stream8.min(Integer::compareTo).get().toString());
        stream1 = Stream.of(1, 3, 5, 7, 9, 2, 4, 6, 8, 10);
        log.info(Boolean.valueOf(stream1.allMatch(e -> e % 2 == 0)).toString());
        stream1 = Stream.of(1, 3, 5, 7, 9, 2, 4, 6, 8, 10);
        log.info(Boolean.valueOf(stream1.anyMatch(e -> e % 2 == 0)).toString());
        stream1 = Stream.of(1, 3, 5, 7, 9, 2, 4, 6, 8, 10);
        log.info(Boolean.valueOf(stream1.noneMatch(e -> e % 2 == 0)).toString());
        stream1 = Stream.of(1, 3, 5, 7, 9, 2, 4, 6, 8, 10);
        log.info(stream1.findAny().get().toString());
        stream1 = Stream.of(1, 3, 5, 7, 9, 2, 4, 6, 8, 10);
        log.info(stream1.findFirst().get().toString());
        stream1 = Stream.of(1, 3, 5, 7, 9, 2, 4, 6, 8, 10);
        log.info(stream1.filter(x -> x % 2 == 0).reduce(Integer::max).get().toString());
        stream1 = Stream.of(1, 3, 5, 7, 9, 2, 4, 6, 8, 10);
        log.info(stream1.reduce(0, (subtotal, element) -> subtotal + element).toString());
        stream9.collect(Collectors.toList()).forEach(e -> log.info(String.valueOf(e[0]).concat(" ").concat(String.valueOf(e[1]))));
        stream1 = Stream.of(1, 3, 5, 7, 9, 2, 4, 6, 8, 10);
        log.info(Arrays.toString(stream1.toArray()));
        stream1 = Stream.of(1, 3, 5, 7, 9, 2, 4, 6, 8, 10);
        stream1.peek(s -> log.info(s.toString())).distinct().collect(Collectors.toList()).forEach(e -> log.info(e.toString()));
    }
}
