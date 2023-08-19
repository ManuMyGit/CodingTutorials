package org.mjjaen.springdata.redis.pubsub;

import lombok.extern.slf4j.Slf4j;
import org.mjjaen.springdata.redis.pubsub.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RedisPublisher {
    @Autowired
    private RedisTemplate<String, Object> pubSubTemplate;

    @Autowired
    private ChannelTopic channelTopic;

    public void publish(Order order){
        log.info("Sending message Sync: {}", order);
        pubSubTemplate.convertAndSend(channelTopic.getTopic(), order);
    }
}
