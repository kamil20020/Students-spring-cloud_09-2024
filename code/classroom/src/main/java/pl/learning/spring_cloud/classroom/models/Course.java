package pl.learning.spring_cloud.classroom.models;

import java.util.UUID;

public record Course (
    UUID id,
    String name
){}
