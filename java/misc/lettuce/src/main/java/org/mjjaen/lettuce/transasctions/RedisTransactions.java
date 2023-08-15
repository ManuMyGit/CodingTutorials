package org.mjjaen.lettuce.transasctions;

import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RedisTransactions {
    @Autowired
    private StatefulRedisConnection<String, String> connection;

    public void runRedisTransactinExample() {
        RedisCommands<String, String> syncCommands = connection.sync();
        syncCommands.multi();
        syncCommands.set("Key1", "Value1");
        String value = syncCommands.get("Key1");
        log.info(value);
        syncCommands.exec();
        value = syncCommands.get("Key1");
        log.info(value);
        log.info("Transaction test done");
    }
}
