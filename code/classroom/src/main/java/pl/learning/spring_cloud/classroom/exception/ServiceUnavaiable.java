package pl.learning.spring_cloud.classroom.exception;

public class ServiceUnavaiable extends RuntimeException{

    public ServiceUnavaiable(String message){
        super(message);
    }
}
