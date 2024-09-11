package pl.learning.spring_cloud.classroom.services;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.learning.spring_cloud.classroom.exception.ServiceUnavaiable;
import pl.learning.spring_cloud.classroom.models.Student;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class StudentService {

    private final RestTemplate restTemplate;

    private final CircuitBreakerFactory circuitBreakerFactory;

    private static final String API_PREFIX = "http://localhost:9087/students";

    public Student fallback(UUID classId, Throwable throwable) throws ServiceUnavaiable{
        throw new ServiceUnavaiable("Student");
    }

    @CircuitBreaker(name = "getStudentById", fallbackMethod = "fallback")
    public Student getById(UUID studentId){

        return restTemplate.getForObject(API_PREFIX + "/{studentId}", Student.class, studentId);
    }

    public void saveStudent(Student student){

        circuitBreakerFactory
            .create("createStudent")
            .run(
                () -> restTemplate.postForObject("/students/", student, Student.class),
                throwable -> "fallback"
            );
    }
}
