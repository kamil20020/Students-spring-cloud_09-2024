package pl.learning.spring_cloud.events;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/events")
public class EventController {

    private final EventService eventService;

    @GetMapping
    public ResponseEntity<Page<EventEntity>> getEventsPage(
        @RequestParam(name = "page") int page,
        @RequestParam(name = "pageSize") int pageSize
    ){
        Pageable pageable = PageRequest.of(page, pageSize);

        Page<EventEntity> gotPage = eventService.getEventsPage(pageable);

        return ResponseEntity.ok(gotPage);
    }
}
