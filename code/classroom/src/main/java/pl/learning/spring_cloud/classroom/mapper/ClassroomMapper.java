package pl.learning.spring_cloud.classroom.mapper;

import pl.learning.spring_cloud.classroom.models.ClassroomEntity;
import pl.learning.spring_cloud.classroom.models.ClassroomHeader;
import pl.learning.spring_cloud.classroom.models.Course;

import java.util.List;

public class ClassroomMapper {

    public static ClassroomHeader map(ClassroomEntity classroom){

        Course foundCourse;

        return new ClassroomHeader(classroom.getId(), null);
    }

    public static List<ClassroomHeader> map(List<ClassroomEntity> classes){

        return classes.stream()
            .map(ClassroomMapper::map)
            .toList();
    }
}
