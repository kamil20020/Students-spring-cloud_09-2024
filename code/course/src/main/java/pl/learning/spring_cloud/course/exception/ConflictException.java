package pl.learning.spring_cloud.course.exception;

public class ConflictException extends RuntimeException{

    public ConflictException(String message){
        super(message);
    }
}
