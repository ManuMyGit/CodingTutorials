package org.mjjaen.lettuce.asynchronous;

import io.lettuce.core.ConnectionFuture;
import io.lettuce.core.RedisFuture;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class RedisAsynchronous {
    @Autowired
    private StatefulRedisConnection<String, String> connection;

    public void runRedisAsyncExample() {
        RedisAsyncCommands<String, String> asyncCommands = connection.async();

        RedisFuture<String> future = asyncCommands.set("myKey", "myValue");
        future.whenCompleteAsync((value, error) -> {
            RedisFuture<String> future2 = asyncCommands.get("myKey");
            future2.whenCompleteAsync((value2, error2) -> {
                log.info(value2);
                RedisFuture<Long> future3 = asyncCommands.del("myKey");
                future3.whenCompleteAsync((value3, error3) -> {
                    log.info("Asynchronous test done");
                });
            });
        });

        try  {
            TimeUnit.SECONDS.sleep(2);
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }
}