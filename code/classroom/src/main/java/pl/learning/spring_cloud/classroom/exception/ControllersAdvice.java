package pl.learning.spring_cloud.classroom.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllersAdvice {

    @ExceptionHandler(value = {IllegalArgumentException.class})
    public ResponseEntity<String> handleInvalidId(IllegalArgumentException e){

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(value = {EntityNotFoundException.class})
    public ResponseEntity<String> handleNotFound(EntityNotFoundException e){

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(value = {ServiceUnavaiable.class})
    public ResponseEntity<String> handleServiceUnavaiable(ServiceUnavaiable e){

        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(e.getMessage());
    }
}
