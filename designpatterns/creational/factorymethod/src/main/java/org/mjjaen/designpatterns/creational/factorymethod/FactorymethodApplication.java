package org.mjjaen.designpatterns.creational.factorymethod;

import org.mjjaen.designpatterns.creational.factorymethod.businessObject.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FactorymethodApplication implements CommandLineRunner {
	public static void main(String[] args) {
		SpringApplication.run(FactorymethodApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		BaseTaskFactoryMethod taskFactoryMethod = new TaskFactoryMethod();
        Task computerTask = taskFactoryMethod.createTask(BaseTaskFactoryMethod.TASKTYPE.COMPUTER, 100);
        Task workTask = taskFactoryMethod.createTask(BaseTaskFactoryMethod.TASKTYPE.WORK, 1000);
        Task homeTask = taskFactoryMethod.createTask(BaseTaskFactoryMethod.TASKTYPE.HOME, 5000);
        
        Task computerTask2 = TaskSimpleFactory.createTask("computer");
		computerTask2.setExecuitonTimeMs(500);
        computerTask2.prepareTask();
        computerTask2.executeTask();
	}
}
