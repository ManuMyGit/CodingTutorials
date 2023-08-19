package org.mjjaen.springdata.redis.examples;

import lombok.extern.slf4j.Slf4j;
import org.mjjaen.springdata.redis.pubsub.RedisPublisher;
import org.mjjaen.springdata.redis.pubsub.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PubSubExamples {
    @Autowired
    private RedisPublisher redisPublisher;

    public PubSubExamples(RedisPublisher redisPublisher) {
        this.redisPublisher = redisPublisher;
    }

    public void runExamplePublisher() {
        log.info("RUNNING PUB/SUB EXAMPLE ...");
        for(int i = 0; i < 3; i ++) {
            Order order = Order.builder().orderId(i).price(i).productName("Order number: " + i).quantity(i).userId(i).build();
            redisPublisher.publish(order);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
