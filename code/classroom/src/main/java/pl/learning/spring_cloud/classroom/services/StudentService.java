package pl.learning.spring_cloud.classroom.services;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.learning.spring_cloud.classroom.exception.ServiceUnavaiable;
import pl.learning.spring_cloud.classroom.models.Student;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class StudentService {

    private final RestTemplate restTemplate;

    private final CircuitBreakerFactory circuitBreakerFactory;

    private static final String API_PREFIX = "http://student/students";

    public Object fallback(Object obj, Throwable throwable) throws ServiceUnavaiable {

        throwable.printStackTrace();

        throw new ServiceUnavaiable("Student microservice is unavaiable");
    }

    @CircuitBreaker(name = "existsStudentById", fallbackMethod = "fallback")
    public boolean doesExist(UUID studentId){

        log.info("Request: does exist by student id {}", studentId);

        String response = restTemplate.getForObject(API_PREFIX + "/{student}/doesExist", String.class, studentId);

        return response.equals("true");
    }

    @CircuitBreaker(name = "getStudentById", fallbackMethod = "fallback")
    public Student getById(UUID studentId) throws EntityNotFoundException {

        log.info("Request: get student by id " + studentId);

        ResponseEntity<Student> response = restTemplate.getForEntity(
            API_PREFIX + "/{studentId}",
            Student.class,
            studentId
        );

        if(response.getStatusCode().equals(HttpStatus.NOT_FOUND)){
            throw new EntityNotFoundException("Student with given id does not exist");
        }

        return (Student) response.getBody();
    }

    @CircuitBreaker(name = "getStudentsByIds", fallbackMethod = "fallback")
    public List<Student> getByIds(Collection<UUID> ids){

        log.info("Request: get students by ids {}", ids);

        String idsStr = ids.stream()
            .map(UUID::toString)
            .collect(Collectors.joining(","));

        Student[] response = restTemplate.getForObject(API_PREFIX + "/ids?ids[]=" + idsStr, Student[].class);

        return Arrays.asList(response);
    }
}
