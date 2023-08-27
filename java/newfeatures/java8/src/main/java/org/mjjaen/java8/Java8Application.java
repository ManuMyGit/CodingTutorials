package org.mjjaen.java8;

import lombok.RequiredArgsConstructor;
import org.mjjaen.java8.base64utilities.Base64Example;
import org.mjjaen.java8.defaultmethods.DefaultMethodsExample;
import org.mjjaen.java8.functionalinterfaces.FunctionalInterfacesExample;
import org.mjjaen.java8.lambdaexpressions.LambdaExpressionsExample;
import org.mjjaen.java8.parrallelarraysorting.ParallelArraySortingExample;
import org.mjjaen.java8.staticmethods.StaticMethodsExample;
import org.mjjaen.java8.streamapi.StreamApiExample;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@RequiredArgsConstructor
@SpringBootApplication
public class Java8Application implements CommandLineRunner {
    private final LambdaExpressionsExample lambdaExpressionsExample;
    private final FunctionalInterfacesExample functionalInterfacesExample;
    private final StreamApiExample streamApiExample;

    private final DefaultMethodsExample defaultMethodsExample;

    private final StaticMethodsExample staticMethodsExample;

    private final ParallelArraySortingExample parallelArraySortingExample;

    private final Base64Example base64Example;

    private static ApplicationContext applicationContext;

    public static void main( String[] args ) {
        applicationContext = SpringApplication.run(Java8Application.class, args);
        applicationContext.getBeanDefinitionNames();
    }

    @Override
    public void run(String... args) throws Exception {
        lambdaExpressionsExample.runExample();
        functionalInterfacesExample.runExample();
        streamApiExample.runExample();
        defaultMethodsExample.runExample();
        staticMethodsExample.runExample();
        parallelArraySortingExample.runExample();
        base64Example.runExample();
    }
}
