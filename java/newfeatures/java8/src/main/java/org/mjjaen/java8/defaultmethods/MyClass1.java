package org.mjjaen.java8.defaultmethods;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@NoArgsConstructor
@Slf4j
public class MyClass1 implements MyInterface{
    @Override
    public void method1() {
        log.info("This code is MyClass1 implementation of method1");
    }
}
