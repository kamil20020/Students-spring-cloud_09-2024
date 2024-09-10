package pl.learning.spring_cloud.student;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/students")
public class StudentController {

    private final StudentService studentService;

    @GetMapping("/{studentId}")
    public ResponseEntity getById(@PathVariable("studentId") String studentIdStr){

        UUID studentId;

        try{
            studentId = UUID.fromString(studentIdStr);
        }
        catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Podano niepoprawne id studenta");
        }

        StudentEntity gotStudent;

        try{
            gotStudent = studentService.getById(studentId);
        }
        catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(gotStudent);
    }

    @GetMapping
    public ResponseEntity<List<StudentEntity>> getAll(){

        List<StudentEntity> gotStudents = studentService.getAll();

        return ResponseEntity.ok(gotStudents);
    }

    @PostMapping
    public ResponseEntity<StudentEntity> create(@RequestBody StudentEntity student){

        StudentEntity createdStudent = studentService.create(student);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdStudent);
    }

    @DeleteMapping("/{studentId}")
    public ResponseEntity deleteById(@PathVariable("studentId") String studentIdStr){

        UUID studentId;

        try{
            studentId = UUID.fromString(studentIdStr);
        }
        catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Podano niepoprawne id studenta");
        }

        try{
            studentService.deleteById(studentId);
        }
        catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
