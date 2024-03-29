package org.mjjaen.springdi.controller;

import org.mjjaen.springdi.services.GreetingService;
import org.springframework.stereotype.Controller;

@Controller
public class MyController {
	private final GreetingService greetingService;
	
	public MyController(GreetingService greetingService) {
		super();
		this.greetingService = greetingService;
	}

	public String hello() {
		System.out.println("Hello!!!!");
		return greetingService.sayGreeting();
	}
}
