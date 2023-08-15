package org.mjjaen.lettuce.reactive;

import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.reactive.RedisReactiveCommands;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class RedisReactive {
    @Autowired
    private StatefulRedisConnection<String, String> connection;

    public void runRedisReactiveExample() {
        RedisReactiveCommands<String, String> reactiveCommands = connection.reactive();

        reactiveCommands.set("myKey", "myValue").subscribe(e -> {
            reactiveCommands.get("myKey").subscribe(e2 -> {
                log.info(e2);
                reactiveCommands.del("myKey").subscribe(e3 -> {
                    log.info("Reactive test done");
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
