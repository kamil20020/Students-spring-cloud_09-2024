package pl.learning.spring_cloud.classroom.config;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventsQueueProducer {

    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.events.routing-key}")
    private String eventsRoutingKey;

    public void sendMessage(String message){

        rabbitTemplate.convertAndSend(eventsRoutingKey, message);
    }
}
