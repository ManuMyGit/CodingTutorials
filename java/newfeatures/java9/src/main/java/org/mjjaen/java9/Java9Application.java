package org.mjjaen.java9;

import lombok.RequiredArgsConstructor;
import org.mjjaen.java9.collection.CollectionExample;
import org.mjjaen.java9.http2.Http2Example;
import org.mjjaen.java9.privateinterfaces.PrivateInterfaceExample;
import org.mjjaen.java9.trywithresources.TryWithResourcesExample;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@RequiredArgsConstructor
@SpringBootApplication
public class Java9Application implements CommandLineRunner {
    private final CollectionExample collectionExample;

    private final PrivateInterfaceExample privateInterfaceExample;

    private final TryWithResourcesExample tryWithResourcesExample;

    private final Http2Example http2Example;

    private static ApplicationContext applicationContext;

    public static void main( String[] args ) {
        applicationContext = SpringApplication.run(Java9Application.class, args);
        applicationContext.getBeanDefinitionNames();
    }

    @Override
    public void run(String... args) throws Exception {
        collectionExample.runExample();
        privateInterfaceExample.runExample();
        tryWithResourcesExample.runExample();
        http2Example.runExample();
    }
}
