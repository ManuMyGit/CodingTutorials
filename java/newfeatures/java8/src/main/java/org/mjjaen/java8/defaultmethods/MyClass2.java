package org.mjjaen.java8.defaultmethods;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class MyClass2 implements MyInterface {
    @Override
    public void method1() {
        log.info("This code is MyClass1 implementation of method1");
    }

    @Override
    public void method2() {
        log.info("MyClass2 overrides the default implementation of method2");
    }
}
