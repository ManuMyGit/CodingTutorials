package org.mjjaen.java8.defaultmethods;

public interface MyInterface {
    org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(MyInterface.class);

    void method1();
    default void method2() {
        log.info("This code is executed by the default implementation of method2");
    }
}
