package org.mjjaen.springdata.jpa;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
@EnableJpaAuditing
public class JpaApplication implements CommandLineRunner
{
    private static ApplicationContext applicationContext;

    public static void main( String[] args )
    {
        applicationContext = SpringApplication.run(JpaApplication.class, args);
        String [] beans = applicationContext.getBeanDefinitionNames();
        System.out.println(beans);
    }

    @Override
    public void run(String... args) throws Exception {

    }
}
