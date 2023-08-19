package org.mjjaen.springdata.redis.pubsub;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mjjaen.springdata.redis.pubsub.model.Order;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@AllArgsConstructor
@Slf4j
@NoArgsConstructor
public class RedisListener implements MessageListener {
    ObjectMapper objectMapper = new ObjectMapper();

    public void onMessage(final Message message, final byte[] pattern) {
        try {
            Order order = objectMapper.readValue(message.toString(), Order.class);
            log.info("Received message: {}", order);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}