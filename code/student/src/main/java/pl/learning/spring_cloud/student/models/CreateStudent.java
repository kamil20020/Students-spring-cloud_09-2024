package pl.learning.spring_cloud.student.models;

import jakarta.validation.constraints.NotBlank;

public record CreateStudent(

    @NotBlank(message = "Firstname is required")
    String firstname,

    @NotBlank(message = "Surname is required")
    String surname
){}
