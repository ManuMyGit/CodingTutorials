package org.mjjaen.designpatterns.creational.singleton;

import org.mjjaen.designpatterns.creational.singleton.businessObject.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SingletonApplication implements CommandLineRunner {
	public static void main(String[] args) {
		SpringApplication.run(SingletonApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		Motorbike motorbike1 = MotorbikeFactory.createCar(SingletonImplementation.SINGLETONLAZY);
		motorbike1.setBrand("BMW");
		motorbike1.setCapacity("750");
		System.out.println("Motorbike 1 is created");
		System.out.println(motorbike1);
		Motorbike motorbike2 = MotorbikeFactory.createCar(SingletonImplementation.SINGLETONLAZY);
		System.out.println("Motorbike 2 is created. This motorbike is equal to motorbike 1");
		System.out.println(motorbike2);
		motorbike1.setBrand("Triumph");
		motorbike1.setCapacity("900");
		System.out.println("Motorbike 1 is modified and motorbike 2 is printed. You can see that motorbike 2 is modified because motorbike 2 is the same than motorbike 1");
		System.out.println(motorbike2);
	}
}
