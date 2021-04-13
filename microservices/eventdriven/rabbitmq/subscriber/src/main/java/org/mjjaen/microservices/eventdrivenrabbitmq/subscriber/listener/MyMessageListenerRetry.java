package org.mjjaen.microservices.eventdrivenrabbitmq.subscriber.listener;

import com.rabbitmq.client.Channel;
import lombok.extern.log4j.Log4j2;
import org.mjjaen.microservices.eventdrivenrabbitmq.rconfiguration.businessObject.MessageBody;
import org.mjjaen.microservices.eventdrivenrabbitmq.rconfiguration.rabbitconfig.DirectQueueConfiguration;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Map;

@Component
@Log4j2
public class MyMessageListenerRetry {
    @Autowired
    private AmqpTemplate template;

    @Autowired
    private MessageConverter messageConverter;

    @RabbitListener(queues = DirectQueueConfiguration.QUEUE_NAME, ackMode = "MANUAL")
    public void consumeNormalQueue(Message message, Channel channel, @Header(required = false, name = "x-death") List<Map<String, Object>> xDeath) throws Exception {
        MessageProperties props = message.getMessageProperties();
        long deliveryTag = message.getMessageProperties().getDeliveryTag();

        Long count = 0L;
        if(xDeath != null) {
            Map<String, Object> xDeathUnqiue = xDeath.stream().filter(element -> element.containsValue(DirectQueueConfiguration.QUEUE_NAME)).findFirst().get();
            count = (Long) xDeathUnqiue.get("count");
        }

        try {
            log.info("Message to be processed: " + message);
            log.info("Attempt: " + (count + 1));
            MessageBody messageBody = (MessageBody) messageConverter.fromMessage(message);
            if (messageBody.getMessage().equals("OK")) {
                log.info("Ack sent! MessageId [id=]" + message.getMessageProperties().getMessageId());
                channel.basicAck(deliveryTag, false);
            } else
                throw new Exception("Forced exception to trigger the retry policy");
        } catch (Exception e) {
            if(count < 7) {
                log.info("Retry sent! MessageId [id=]" + message.getMessageProperties().getMessageId());
                channel.basicNack(deliveryTag, false, false);
            } else {
                log.info("Event sent to the dead queue! MessageId [id=]" + message.getMessageProperties().getMessageId());
                template.convertAndSend(DirectQueueConfiguration.DEAD_QUEUE_EXCHANGE, DirectQueueConfiguration.DEAD_QUEUE_ROUTING_KEY, message);
                channel.basicAck(deliveryTag, false);
            }
        }
    }
}
