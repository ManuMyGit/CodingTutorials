package org.mjjaen.java8.staticmethods;

import lombok.extern.slf4j.Slf4j;
import org.mjjaen.java8.Example;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class StaticMethodsExample implements Example {
    @Override
    public void runExample() {
        log.info("Example of static method ...");
        MyInterface.myStaticMethod();
    }
}
