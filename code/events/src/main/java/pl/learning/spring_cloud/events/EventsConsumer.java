package pl.learning.spring_cloud.events;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class EventsConsumer {

    private final EventService eventService;

    @RabbitListener(queues = "${rabbitmq.events.queue.name}")
    public void receiveMessage(String message){

        EventEntity gotEvent = eventService.create(message);

        log.info(gotEvent.toString());
    }
}
