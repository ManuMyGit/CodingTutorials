package org.mjjaen.java8.defaultmethods;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface MyInterface {
    Logger log = LoggerFactory.getLogger(MyInterface.class);

    void method1();
    default void method2() {
        log.info("This code is executed by the default implementation of method2");
    }
}
