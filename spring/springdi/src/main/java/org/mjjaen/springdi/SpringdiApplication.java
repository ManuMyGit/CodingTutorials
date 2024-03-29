package org.mjjaen.springdi;

import org.mjjaen.springdi.controller.ConstructorInjectedController;
import org.mjjaen.springdi.controller.MyController;
import org.mjjaen.springdi.controller.PropertyInjectedController;
import org.mjjaen.springdi.controller.SetterInjectedController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SpringdiApplication {
	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(SpringdiApplication.class, args);
		
		MyController controller = (MyController)ctx.getBean("myController");
		System.out.println(controller.hello());
		System.out.println(ctx.getBean(PropertyInjectedController.class).sayHello());
		System.out.println(ctx.getBean(SetterInjectedController.class).sayHello());
		System.out.println(ctx.getBean(ConstructorInjectedController.class).sayHello());
	}
}
