package org.mjjaen.springdi.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mjjaen.springdi.services.GreetingServiceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConstructorInjectedControllerTest {
	private ConstructorInjectedController constructorInjectedController;

	@BeforeAll
	public void setUp() throws Exception {
		this.constructorInjectedController = new ConstructorInjectedController(new GreetingServiceImpl());
	}

	@Test
	public void test() {
		assertEquals(GreetingServiceImpl.HELLO, constructorInjectedController.sayHello());
	}
}
