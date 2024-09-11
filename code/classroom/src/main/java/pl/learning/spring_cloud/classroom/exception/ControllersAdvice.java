package pl.learning.spring_cloud.classroom.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllersAdvice {

    @ExceptionHandler(value = {IllegalArgumentException.class})
    public ResponseEntity<Void> handleBadRequest(Exception e){

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @ExceptionHandler(value = {EntityNotFoundException.class})
    public ResponseEntity<Void> handleNotFound(Exception e){

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @ExceptionHandler(value = {ServiceUnavaiable.class})
    public ResponseEntity<String> handleServiceUnavaiable(Exception e){

        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(e.getMessage());
    }
}
