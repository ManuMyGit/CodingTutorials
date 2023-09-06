package org.mjjaen.springdata.redis.examples;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ListOperationsExamples implements Examples {
    @Autowired
    private final RedisTemplate stringRedisTemplate;
    private final ListOperations<String, String> listOperations;

    public ListOperationsExamples(RedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
        listOperations = stringRedisTemplate.opsForList();
    }

    @Override
    public void runExamples() {
        log.info("RUNNING LIST EXAMPLES ...");
        log.info("Adding myValue1, myValue2, myValue3 and myValue1 to the list ...");
        listOperations.leftPush("myList", "myValue1");
        listOperations.leftPush("myList", "myValue2");
        listOperations.leftPush("myList", "myValue3");
        listOperations.rightPush("myList", "myValue1");
        log.info("Getting the size of the list (4 because list allows duplicate elements) ...");
        log.info(listOperations.size("myList").toString());
        log.info("Deleting the list ...");
        stringRedisTemplate.delete("myList");
    }
}
