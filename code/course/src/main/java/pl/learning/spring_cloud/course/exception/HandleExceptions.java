package pl.learning.spring_cloud.course.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class HandleExceptions {

    @ExceptionHandler(value = {IllegalArgumentException.class})
    public ResponseEntity<String> handleInvalidId(RuntimeException e){

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Got invalid course id");
    }

    @ExceptionHandler(value = {EntityNotFoundException.class})
    public ResponseEntity<String> handleNotFound(EntityNotFoundException e){

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(value = {ConflictException.class})
    public ResponseEntity<String> handleConflict(ConflictException e){

        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<Map<String, String>> handleFormException(MethodArgumentNotValidException e){

        Map<String, String> fieldErrors = new HashMap<>();

        e.getBindingResult().getFieldErrors().forEach(error -> {
            fieldErrors.put(error.getField(), error.getDefaultMessage());
        });

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(fieldErrors);
    }
}
