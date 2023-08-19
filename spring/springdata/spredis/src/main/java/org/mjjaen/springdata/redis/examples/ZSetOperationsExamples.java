package org.mjjaen.springdata.redis.examples;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ZSetOperationsExamples implements Examples {
    @Autowired
    private RedisTemplate stringRedisTemplate;
    private ZSetOperations<String, String> sortedSetOperations;

    public ZSetOperationsExamples(RedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
        sortedSetOperations = stringRedisTemplate.opsForZSet();
    }

    @Override
    public void runExamples() {
        log.info("RUNNING SORTED SET EXAMPLES ...");
        log.info("Adding myValue1 with the score 0.5 ...");
        sortedSetOperations.add("mySortSet", "myValue1", 0.5);
        log.info("Adding myValue2 with the score 0.4 ...");
        sortedSetOperations.add("mySortSet", "myValue2", 0.4);
        log.info("Adding myValue3 with the score 0.6 ...");
        sortedSetOperations.add("mySortSet", "myValue3", 0.6);
        log.info("Getting the rank of myValue1 ...");
        log.info(sortedSetOperations.rank("mySortSet", "myValue1").toString());
        log.info("Getting the rank of myValue2 ...");
        log.info(sortedSetOperations.rank("mySortSet", "myValue2").toString());
        log.info("Getting the rank of myValue3 ...");
        log.info(sortedSetOperations.rank("mySortSet", "myValue3").toString());
        log.info("Deleting the sorted set ...");
        stringRedisTemplate.delete("mySortSet");
        log.info("Getting the size of the sorted set mySortSet ...");
        log.info(sortedSetOperations.size("mySortSet").toString());
    }
}
