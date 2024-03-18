package dev.renvl.publisher;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RabbitMQProducerTest {

    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private RabbitMQProducer rabbitMQProducer;

    @Test
    void sendMessage() {
        Object message = "Test message";
        rabbitTemplate.convertAndSend(message);
        verify(rabbitTemplate, times(1)).convertAndSend(message);
        rabbitMQProducer.sendMessage(message);
    }

    @Test
    void updateMessage() {
        Object updatedMessage = "Updated test message";
        rabbitTemplate.convertAndSend(updatedMessage);
        verify(rabbitTemplate, times(1)).convertAndSend(updatedMessage);
        rabbitMQProducer.updateMessage(updatedMessage);
    }
}
