package org.mjjaen.java8.lambdaexpressions;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.mjjaen.java8.Example;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
public class LambdaExpressionsExample implements Example {
    @Override
    public void runExample() {
        log.info("Testing zero parameters lambda...");
        Runnable runnable = () -> log.info("Hello from a lambda with no parameters!");
        runnable.run();
        log.info("Testing one parameter lambda...");
        OneParamOperation oneParamOperation = name -> {
            log.info("Lambda with more than one statement");
            return "Hello my dear ".concat(name);
        };
        log.info(oneParamOperation.saySomething("Turgon"));
        log.info("Testing multiple parameters lambda...");
        TwoParamsOperation<Integer> twoParamOperation = (value1, value2) -> value1 + value2;
        log.info(twoParamOperation.operation(1, 2).toString());
        TwoParamsOperation<Double> twoParamOperationDouble = (value1, value2) -> value1 * value2;
        log.info(twoParamOperationDouble.operation(3.5, 6.1).toString());
        log.info("Testing method references with reference to a static method...");
        List<String> messages = Arrays.asList("hello", "baeldung", "readers!");
        messages.forEach(System.out::println);
        log.info("Testing method references with reference to an instance method of a particular object...");
        String[] stringArray = { "India", "Australia", "England", "Newzealand", "SouthAfrica", "Bangladesh", "WestIndies", "Zimbabwe" };
        Arrays.sort(stringArray, String::compareToIgnoreCase);
        log.info(Arrays.toString(stringArray));
        log.info("Testing method references with reference to an instance method of an arbitrary object of a particular type...");
        List<Integer> numbers = Arrays.asList(5, 3, 50, 24, 40, 2, 9, 18);
        numbers.stream().sorted(Integer::compareTo).forEach(x -> log.info(x.toString()));
        log.info("Testing method references with reference to a constructor...");
        List<String> bikeBrands = Arrays.asList("Giant", "Scott", "Trek", "GT");
        bikeBrands.stream()
                .map(Bicycle::new)
                .toArray(Bicycle[]::new);
    }

    @ToString
    class Bicycle {
        private String brand;
        public Bicycle(String brand) {
            this.brand = brand;
        }
    }
}
