package dev.renvl.publisher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQProducer.class);

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.create.routing.key}")
    private String createRoutingKey;

    @Value("${rabbitmq.update.routing.key}")
    private String updateRoutingKey;

    private final RabbitTemplate rabbitTemplate;

    public RabbitMQProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(Object message) {
        rabbitTemplate.convertAndSend(exchange, createRoutingKey, message);
        LOGGER.info("Message sent for creation -> {}", message);
    }

    public void updateMessage(Object updatedMessage) {
        rabbitTemplate.convertAndSend(exchange, updateRoutingKey, updatedMessage);
        LOGGER.info("Message sent for update -> {}", updatedMessage);
    }
}
