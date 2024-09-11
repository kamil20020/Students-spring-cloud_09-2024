package pl.learning.spring_cloud.classroom.models;

import java.util.UUID;

public record Student(
    UUID id,
    String firstname,
    String surname
){}
