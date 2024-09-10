package com.example.demo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ClassroomService {

    private final ClassroomRepository classroomRepository;

    public ClassroomEntity getClassById(UUID classId){

        return null;
    }

    public List<ClassroomHeader> getClasses(){

        return List.of();
    }

    public List<Course> getByStudentId(UUID studentId){

        return List.of();
    }

    public List<ClassroomEntity> getStudentClasses(UUID studentId){

        return List.of();
    }

    public List<Student> getClassStudents(UUID classId){

        return List.of();
    }
}
