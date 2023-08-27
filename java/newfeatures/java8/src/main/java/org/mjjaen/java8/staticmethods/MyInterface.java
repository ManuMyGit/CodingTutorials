package org.mjjaen.java8.staticmethods;

public interface MyInterface {
    org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(org.mjjaen.java8.defaultmethods.MyInterface.class);
    static void myStaticMethod() {
        log.info("This is the implementation of a static method in an interface");
    }
}
