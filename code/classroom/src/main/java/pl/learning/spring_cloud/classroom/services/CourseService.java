package pl.learning.spring_cloud.classroom.services;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.learning.spring_cloud.classroom.exception.ServiceUnavaiable;
import pl.learning.spring_cloud.classroom.models.Course;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CourseService {

    private final RestTemplate restTemplate;

    private final CircuitBreakerFactory circuitBreakerFactory;

    private static final String URL_PREFIX = "http://course/courses";

    public Object fallback(Object obj, Throwable throwable) throws ServiceUnavaiable {

        throwable.printStackTrace();

        throw new ServiceUnavaiable("Course microservice is unavaiable");
    }

    @CircuitBreaker(name = "existsCourseById", fallbackMethod = "fallback")
    public boolean existsById(UUID courseId){

        String response = restTemplate.getForObject(URL_PREFIX + "/{courseId}/exists", String.class, courseId);

        return response.equals("true");
    }

    @CircuitBreaker(name = "getCourseById", fallbackMethod = "fallback")
    public Course getById(UUID courseId) throws EntityNotFoundException {

        ResponseEntity<Course> response = restTemplate.getForEntity(URL_PREFIX + "/{classId}", Course.class, courseId);

        if(response.getStatusCode().equals(HttpStatus.NOT_FOUND)){
            throw new EntityNotFoundException("Course with given id does not exist");
        }

        return response.getBody();
    }

    @CircuitBreaker(name = "getCoursesByIds", fallbackMethod = "fallback")
    public List<Course> getByIds(Collection<UUID> ids){

        String idsStr = ids.stream()
            .map(UUID::toString)
            .collect(Collectors.joining(","));

        Course[] response = restTemplate.getForObject(URL_PREFIX + "/ids?ids[]=" + idsStr, Course[].class);

        return Arrays.asList(response);
    }
}
