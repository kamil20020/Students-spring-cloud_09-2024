package pl.learning.spring_cloud.classroom.mapper;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.learning.spring_cloud.classroom.models.ClassroomEntity;
import pl.learning.spring_cloud.classroom.models.ClassroomHeader;
import pl.learning.spring_cloud.classroom.models.Course;
import pl.learning.spring_cloud.classroom.services.CourseService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ClassroomMapper {

    private final CourseService courseService;

    public ClassroomHeader map(ClassroomEntity classroom) throws EntityNotFoundException {

        Course foundCourse = null;

        if(classroom.getCourseId() != null){
            foundCourse = courseService.getById(classroom.getCourseId());
        }

        return new ClassroomHeader(classroom.getId(), foundCourse);
    }

    public List<ClassroomHeader> map(List<ClassroomEntity> classes){

        return classes.stream()
            .map(this::map)
            .collect(Collectors.toList());
    }
}
