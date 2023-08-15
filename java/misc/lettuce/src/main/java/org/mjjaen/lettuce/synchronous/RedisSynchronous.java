package org.mjjaen.lettuce.synchronous;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class RedisSynchronous {
    @Autowired
    private StatefulRedisConnection<String, String> connection;

    public void runRedisSyncExample() {
        RedisCommands<String, String> syncCommands = connection.sync();

        syncCommands.set("myKey", "myValye");
        log.info(syncCommands.get("myKey"));
        syncCommands.del("myKey");
        log.info(syncCommands.get("myKey"));

        log.info("Synchronous test done");
    }
}
