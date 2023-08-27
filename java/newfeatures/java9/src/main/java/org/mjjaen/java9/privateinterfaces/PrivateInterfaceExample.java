package org.mjjaen.java9.privateinterfaces;

import lombok.extern.slf4j.Slf4j;
import org.mjjaen.java9.Example;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PrivateInterfaceExample implements Example {
    @Override
    public void runExample() {
        log.info("Running collection new methods example ...");
        log.info("Calling interface method which makes use of a private method ...");
        MyInterface myInterface = new MyClass();
        myInterface.bar();
        log.info("Calling static interface method which makes use of a static private method ...");
        MyInterface.buzz();
    }
}
