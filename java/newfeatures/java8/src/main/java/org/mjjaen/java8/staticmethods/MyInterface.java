package org.mjjaen.java8.staticmethods;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface MyInterface {
    Logger log = LoggerFactory.getLogger(MyInterface.class);
    static void myStaticMethod() {
        log.info("This is the implementation of a static method in an interface");
    }
}
