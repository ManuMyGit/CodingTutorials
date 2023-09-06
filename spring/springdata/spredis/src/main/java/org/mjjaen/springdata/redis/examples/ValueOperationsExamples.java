package org.mjjaen.springdata.redis.examples;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ValueOperationsExamples implements Examples {
    @Autowired
    private final RedisTemplate stringRedisTemplate;
    private final ValueOperations<String, String> valueOperations;

    public ValueOperationsExamples(RedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
        valueOperations = stringRedisTemplate.opsForValue();
    }

    @Override
    public void runExamples() {
        log.info("RUNNING VALUE EXAMPLES ...");
        log.info("Adding key MyKey with value MyValue ...");
        valueOperations.setIfAbsent("MyKey", "MyValue");
        log.info("Getting the value of the key MyKey ...");
        log.info(valueOperations.get("MyKey"));
        log.info("Appending the value Appended to the key MyKey ...");
        valueOperations.append("MyKey", "Appended");
        log.info("Getting the value of the key MyKey ...");
        log.info(valueOperations.get("MyKey"));
        log.info("Deleting MyKey ...");
        stringRedisTemplate.delete("MyKey");
        log.info("Getting the value of the key MyKey ...");
        log.info(valueOperations.get("MyKey") != null ? valueOperations.get("MyKey") : "Key not found.");
    }
}
