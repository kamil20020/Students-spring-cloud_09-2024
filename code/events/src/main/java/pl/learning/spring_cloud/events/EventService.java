package pl.learning.spring_cloud.events;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;

    public Page<EventEntity> getEventsPage(Pageable pageable){

        return eventRepository.findAll(pageable);
    }

    public EventEntity create(String message){

        EventEntity newEvent = EventEntity.builder()
            .description(message)
            .dateTime(LocalDateTime.now())
            .build();

        return eventRepository.save(newEvent);
    }
}
