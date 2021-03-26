package org.mjjaen.designpatterns.creational.singleton;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mjjaen.designpatterns.creational.singleton.businessObject.Motorbike;
import org.mjjaen.designpatterns.creational.singleton.businessObject.MotorbikeFactory;
import org.mjjaen.designpatterns.creational.singleton.businessObject.SingletonImplementation;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class SingletonTest {
    @Test
    public void test() {
        Motorbike motorbike1 = MotorbikeFactory.createCar(SingletonImplementation.SINGLETONLAZY);
        motorbike1.setBrand("BMW");
        motorbike1.setCapacity("750");
        System.out.println("Motorbike 1 is created");
        System.out.println(motorbike1);
        Motorbike motorbike2 = MotorbikeFactory.createCar(SingletonImplementation.SINGLETONLAZY);
        System.out.println("Motorbike 2 is created. This motorbike is equal to motorbike 1");
        Assertions.assertEquals(motorbike1.getBrand(), motorbike2.getBrand());
        Assertions.assertEquals(motorbike1.getCapacity(), motorbike2.getCapacity());
        System.out.println(motorbike2);
        motorbike1.setBrand("Triumph");
        motorbike1.setCapacity("900");
        Assertions.assertEquals(motorbike2.getBrand(), "Triumph");
        Assertions.assertEquals(motorbike2.getCapacity(), "900");
        System.out.println("Motorbike 1 is modified and motorbike 2 is printed. You can see that motorbike 2 is modified because motorbike 2 is the same than motorbike 1");
        System.out.println(motorbike2);
    }
}
