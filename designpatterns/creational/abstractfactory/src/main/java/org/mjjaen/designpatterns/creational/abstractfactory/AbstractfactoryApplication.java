package org.mjjaen.designpatterns.creational.abstractfactory;

import org.mjjaen.designpatterns.creational.abstractfactory.businessObject.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AbstractfactoryApplication implements CommandLineRunner {
	public static void main(String[] args) {
		SpringApplication.run(AbstractfactoryApplication.class, args);
	}

	@SuppressWarnings("unused")
	@Override
	public void run(String... args) throws Exception {
        //get task factory
	    AbstractFactory taskFactory = FactoryProvider.getFactory("task");
        //Get home task
        Task task1 = (Task)taskFactory.create("home");
        task1.setExecuitonTimeMs(500);
        task1.prepareTask();
        task1.executeTask();
        //Get work task
        Task task2 = (Task)taskFactory.create("work");
        task2.setExecuitonTimeMs(2500);
        task2.prepareTask();
        task2.executeTask();
        //Get computer task
        Task task3 = (Task)taskFactory.create("computer");
        task3.setExecuitonTimeMs(50);
        task3.prepareTask();
        task3.executeTask();

        //get car factory
        AbstractFactory carFactory = FactoryProvider.getFactory("car");
        Car car1 = (Car)carFactory.create("manual");
        car1.turnOnEngine();
        Car car2 = (Car)carFactory.create("automatic");
        car2.turnOnEngine();
	}
}
