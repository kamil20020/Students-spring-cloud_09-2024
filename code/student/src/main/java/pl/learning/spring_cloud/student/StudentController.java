package pl.learning.spring_cloud.student;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/students")
public class StudentController {

    private final StudentService studentService;

    @GetMapping("{studentId}/doesExist")
    public ResponseEntity<Boolean> existsById(@PathVariable("studentId") String studentIdStr){

        UUID studentId = UUID.fromString(studentIdStr);

        boolean doesExists = studentService.existsById(studentId);

        return ResponseEntity.ok(doesExists);
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<StudentEntity> getById(@PathVariable("studentId") String studentIdStr){

        UUID studentId = UUID.fromString(studentIdStr);

        StudentEntity gotStudent = studentService.getById(studentId);

        return ResponseEntity.ok(gotStudent);
    }

    @GetMapping
    public ResponseEntity<List<StudentEntity>> getAll(){

        List<StudentEntity> gotStudents = studentService.getAll();

        return ResponseEntity.ok(gotStudents);
    }

    @GetMapping("/ids")
    public ResponseEntity<List<StudentEntity>> getByIds(@RequestParam(required = true, name = "ids[]") String[] idsStrs){

        List<UUID> ids = Arrays.stream(idsStrs)
            .map(UUID::fromString)
            .collect(Collectors.toList());

        List<StudentEntity> gotStudents = studentService.getByIds(ids);

        return ResponseEntity.ok(gotStudents);
    }

    @PostMapping
    public ResponseEntity<StudentEntity> create(@RequestBody StudentEntity student){

        StudentEntity createdStudent = studentService.create(student);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdStudent);
    }

    @DeleteMapping("/{studentId}")
    public ResponseEntity<Void> deleteById(@PathVariable("studentId") String studentIdStr){

        UUID studentId = UUID.fromString(studentIdStr);

        studentService.deleteById(studentId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
