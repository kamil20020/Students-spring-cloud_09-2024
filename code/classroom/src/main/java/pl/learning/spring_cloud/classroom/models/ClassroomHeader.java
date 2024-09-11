package pl.learning.spring_cloud.classroom.models;

import java.util.UUID;

public record ClassroomHeader(
    UUID id,
    Course course
){}
