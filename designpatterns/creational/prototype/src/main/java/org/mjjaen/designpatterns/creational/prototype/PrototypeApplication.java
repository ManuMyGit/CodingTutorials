package org.mjjaen.designpatterns.creational.prototype;

import org.mjjaen.designpatterns.creational.prototype.businessObject.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PrototypeApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(PrototypeApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Customer customerShallow = CustomerPrototypeManager.getClonedDocument("customerShallow");
		customerShallow.setFirstName("MyFirstName (shallow)");
		System.out.println(customerShallow);
		Customer customerDeep = CustomerPrototypeManager.getClonedDocument("customerDeep");
		customerDeep.setFirstName("MyFirstName (deep)");
		System.out.println(customerDeep);
	}
}
