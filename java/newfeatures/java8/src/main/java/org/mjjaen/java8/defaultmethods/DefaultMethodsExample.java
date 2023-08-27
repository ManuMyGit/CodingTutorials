package org.mjjaen.java8.defaultmethods;

import lombok.extern.slf4j.Slf4j;
import org.mjjaen.java8.Example;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DefaultMethodsExample implements Example {
    @Override
    public void runExample() {
        MyClass1 myClass1 = new MyClass1();
        MyClass2 myClass2 = new MyClass2();
        log.info("Testing default methods ...");
        log.info("myClass1 executes method1 ...");
        myClass1.method1();
        log.info("myClass1 executes method2 ...");
        myClass1.method2();
        log.info("myClass2 executes method1 ...");
        myClass2.method1();
        log.info("myClass2 executes method2 ...");
        myClass2.method2();
    }
}
