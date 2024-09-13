package pl.learning.spring_cloud.student;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class HandleExceptions {

    @ExceptionHandler(value = {IllegalArgumentException.class})
    public ResponseEntity<Void> handleBadRequest(RuntimeException e){

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @ExceptionHandler(value = {EntityNotFoundException.class})
    public ResponseEntity<Void> handleNotFound(RuntimeException e){

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
