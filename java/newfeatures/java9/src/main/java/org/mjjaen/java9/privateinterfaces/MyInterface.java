package org.mjjaen.java9.privateinterfaces;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface MyInterface {
    Logger log = LoggerFactory.getLogger(MyInterface.class);

    default void bar() {
        log.info("Hello");
        baz();
    }

    private void baz() {
        log.info(" world!");
    }

    static void buzz() {
        log.info("Hello");
        staticBaz();
    }

    private static void staticBaz() {
        log.info(" static world!");
    }
}
