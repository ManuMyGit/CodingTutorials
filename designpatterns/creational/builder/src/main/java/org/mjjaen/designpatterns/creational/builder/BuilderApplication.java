package org.mjjaen.designpatterns.creational.builder;

import org.mjjaen.designpatterns.creational.builder.businessObject.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Date;
import java.time.Instant;

@SpringBootApplication
public class BuilderApplication implements CommandLineRunner {
	public static void main(String[] args) {
		SpringApplication.run(BuilderApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		Task task = new TaskBuilder(5).withDescription("Hello").withSummary("Test").build();
		System.out.println(task);
		TaskInternalBuilder taskInternalBuilder = new TaskInternalBuilder.Builder(2).witDone(false).withDescription("Bye").withDueDate(Date.from(Instant.now())).withSummary("Test").build();
		System.out.println(taskInternalBuilder);
	}
}
