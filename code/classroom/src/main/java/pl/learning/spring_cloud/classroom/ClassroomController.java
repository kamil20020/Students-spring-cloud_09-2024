package pl.learning.spring_cloud.classroom;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.learning.spring_cloud.classroom.mapper.ClassroomMapper;
import pl.learning.spring_cloud.classroom.models.ClassroomEntity;
import pl.learning.spring_cloud.classroom.models.ClassroomHeader;
import pl.learning.spring_cloud.classroom.models.Course;
import pl.learning.spring_cloud.classroom.models.Student;
import pl.learning.spring_cloud.classroom.services.ClassroomService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/classrooms")
public class ClassroomController {

    private final ClassroomService classroomService;
    private final ClassroomMapper classroomMapper;

    @GetMapping("/{classId}")
    public ResponseEntity<ClassroomHeader> getClassHeaderById(@PathVariable("classId") String classIdStr){

        UUID classId = UUID.fromString(classIdStr);

        ClassroomEntity foundClass = classroomService.getClassById(classId);
        ClassroomHeader convertedClass = classroomMapper.map(foundClass);

        return ResponseEntity.ok(convertedClass);
    }

    @GetMapping
    public ResponseEntity<List<ClassroomHeader>> getClassesHeaders(){

        List<ClassroomEntity> gotClasses = classroomService.getAll();
        List<ClassroomHeader> convertedClasses = classroomMapper.map(gotClasses);

        return ResponseEntity.ok(convertedClasses);
    }

    @GetMapping("/students/{studentId}")
    public ResponseEntity<List<ClassroomHeader>> getStudentClasses(@PathVariable("studentId") String studentIdStr){

        UUID studentId = UUID.fromString(studentIdStr);

        List<ClassroomEntity> gotClasses = classroomService.getStudentClasses(studentId);
        List<ClassroomHeader> convertedClasses = classroomMapper.map(gotClasses);

        return ResponseEntity.ok(convertedClasses);
    }

    @GetMapping("/students/{studentId}/courses")
    public ResponseEntity<List<Course>> getStudentCourses(@PathVariable("studentId") String studentIdStr){

        UUID studentId = UUID.fromString(studentIdStr);

        List<Course> courses = classroomService.getCoursesByStudentId(studentId);

        return ResponseEntity.ok(courses);
    }

    @GetMapping("/{classId}/students")
    public ResponseEntity<List<Student>> getClassStudents(@PathVariable("classId") String classIdStr){

        UUID classId = UUID.fromString(classIdStr);

        List<Student> gotStudents = classroomService.getClassStudents(classId);

        return ResponseEntity.ok(gotStudents);
    }

    @PostMapping("/{courseId}")
    public ResponseEntity<ClassroomHeader> createClass(@PathVariable("courseId") String courseIdStr){

        UUID courseId = UUID.fromString(courseIdStr);

        ClassroomEntity createdClass = classroomService.createClass(courseId);
        ClassroomHeader convertedClass = classroomMapper.map(createdClass);

        return ResponseEntity.status(HttpStatus.CREATED).body(convertedClass);
    }

    @PostMapping("/{classId}/students/{studentId}")
    public ResponseEntity<Student> addStudentToClass(@PathVariable("classId") String classIdStr, @PathVariable("studentId") String studentIdStr){

        UUID classId = UUID.fromString(classIdStr);
        UUID studentId = UUID.fromString(studentIdStr);

        Student student = classroomService.addStudentToClass(studentId, classId);

        return ResponseEntity.ok(student);
    }

    @DeleteMapping("/{classId}/students/{studentId}")
    public ResponseEntity<Void> removeStudentFromClass(@PathVariable("classId") String classIdStr, @PathVariable("studentId") String studentIdStr){

        UUID classId = UUID.fromString(classIdStr);
        UUID studentId = UUID.fromString(studentIdStr);

        classroomService.removeStudentFromClass(studentId, classId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/{classId}")
    public ResponseEntity<Void> removeClassById(@PathVariable("classId") String classIdStr){

        UUID classId = UUID.fromString(classIdStr);

        classroomService.removeClassById(classId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
