package org.mjjaen.syncasyncserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@SpringBootApplication
@EnableAsync
public class SyncAsyncServerApplication
{
    public static void main(String[] args) {
        SpringApplication.run(SyncAsyncServerApplication.class, args);
    }

    @Bean
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(100);
        executor.setMaxPoolSize(100);
        executor.setQueueCapacity(1000); //Default Integer.MAX_VALUE
        executor.setThreadNamePrefix("Async-Exec-");
        executor.initialize();
        return executor;
    }
}
