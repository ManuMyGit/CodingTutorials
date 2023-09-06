package org.mjjaen.springdata.redis.examples;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@Slf4j
public class SetOperationsExamples implements Examples {
    @Autowired
    private final RedisTemplate stringRedisTemplate;
    private final SetOperations<String, String> setOperations;

    public SetOperationsExamples(RedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
        setOperations = stringRedisTemplate.opsForSet();
    }

    @Override
    public void runExamples() {
        log.info("RUNNING SET EXAMPLES ...");
        log.info("Adding myValue1, myValue2, myValue1 to mySet1 ...");
        setOperations.add("mySet1", "value1", "value1", "value2");
        log.info("Adding myValue2 to mySet1 ...");
        setOperations.add("mySet2", "value2");
        log.info("Getting the size of mySet1 (2 because set does not allow duplicate elements) ...");
        log.info(setOperations.size("mySet1").toString());
        Set<String> diff = setOperations.difference("mySet1", "mySet2");
        log.info("Getting the different elements between both sets ...");
        diff.forEach(e -> log.info(e));
        log.info("Deleting the sets ...");
        stringRedisTemplate.delete("mySet1");
        stringRedisTemplate.delete("mySet2");
        log.info("Getting the size of the set mySet1 ...");
        log.info(setOperations.size("mySet1").toString());
        log.info("Getting the size of the set mySet2 ...");
        log.info(setOperations.size("mySet2").toString());
    }
}
