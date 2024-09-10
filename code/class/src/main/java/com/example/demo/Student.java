package com.example.demo;

import java.util.UUID;

public record Student(
    UUID id,
    String firstname,
    String surname
){}
