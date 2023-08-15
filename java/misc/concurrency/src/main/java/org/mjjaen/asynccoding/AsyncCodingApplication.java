package org.mjjaen.asynccoding;

import org.mjjaen.asynccoding.executableExamples.Executable;
import org.mjjaen.asynccoding.executableExamples.ExecutableType;
import org.mjjaen.asynccoding.executableExamples.FactoryExecutable;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AsyncCodingApplication implements CommandLineRunner
{
    public static void main( String[] args ) {
        SpringApplication.run(AsyncCodingApplication.class, args);
    }

    @Override
    public void run(String... args) throws IllegalArgumentException {
        Executable executable;
        executable = FactoryExecutable.createExecutableExample(ExecutableType.COMPLETABLE_FUTURE);
        executable.execute(46);
    }
}