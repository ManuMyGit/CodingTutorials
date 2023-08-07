package org.mjjaen.springdi.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mjjaen.springdi.services.GreetingServiceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PropertyInjectedControllerTest {
	private PropertyInjectedController propertyInjectedController;

	@BeforeAll
	public void setUp() throws Exception {
		this.propertyInjectedController = new PropertyInjectedController();
		this.propertyInjectedController.greetingServiceImpl = new GreetingServiceImpl();
	}

	@Test
	public void test() {
		assertEquals(GreetingServiceImpl.HELLO, propertyInjectedController.sayHello());
	}

}
