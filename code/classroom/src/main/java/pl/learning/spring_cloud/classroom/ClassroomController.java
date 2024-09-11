package pl.learning.spring_cloud.classroom;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.learning.spring_cloud.classroom.models.ClassroomEntity;
import pl.learning.spring_cloud.classroom.models.ClassroomHeader;
import pl.learning.spring_cloud.classroom.models.Student;
import pl.learning.spring_cloud.classroom.services.ClassroomService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/classrooms")
public class ClassroomController {

    private final ClassroomService classroomService;

    @GetMapping
    public ResponseEntity<List<ClassroomHeader>> getClassesHeaders(){

        List<ClassroomHeader> gotClasses = classroomService.getClasses();

        return ResponseEntity.ok(gotClasses);
    }

    @GetMapping("/students/{studentId}")
    public ResponseEntity getStudentClasses(@PathVariable("studentId") String studentIdStr){

        UUID studentId = UUID.fromString(studentIdStr);

        List<ClassroomHeader> gotClasses = classroomService.getStudentClasses(studentId);

        return ResponseEntity.ok(gotClasses);
    }

    @GetMapping("/{classId}/students")
    public ResponseEntity getClassStudents(@PathVariable("classId") String classIdStr){

        UUID classId = UUID.fromString(classIdStr);

        List<Student> gotStudents = classroomService.getClassStudents(classId);

        return ResponseEntity.ok(gotStudents);
    }

    @PostMapping
    public ResponseEntity createClass(){

        ClassroomEntity createdClass = classroomService.createClass(UUID.randomUUID());

        return ResponseEntity.status(HttpStatus.CREATED).body(createdClass);
    }

    @PostMapping("/{classId}/students/{studentId}")
    public ResponseEntity addStudentToClass(@PathVariable("classId") String classIdStr, @PathVariable("studentId") String studentIdStr){

        UUID classId = UUID.fromString(classIdStr);
        UUID studentId = UUID.fromString(studentIdStr);

        ClassroomEntity classHeader = classroomService.addStudentToClass(studentId, classId);

        return ResponseEntity.ok(classHeader);
    }

    @DeleteMapping("/{classId}/students/{studentId}")
    public ResponseEntity removeStudentFromClass(@PathVariable("classId") String classIdStr, @PathVariable("studentId") String studentIdStr){

        UUID classId = UUID.fromString(classIdStr);
        UUID studentId = UUID.fromString(studentIdStr);

        classroomService.removeStudentFromClass(studentId, classId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/{classId}")
    public ResponseEntity removeClassById(@PathVariable("classId") String classIdStr){

        UUID classId = UUID.fromString(classIdStr);

        classroomService.removeClassById(classId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
