package pl.learning.spring_cloud.course;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.learning.spring_cloud.course.models.CourseEntity;
import pl.learning.spring_cloud.course.models.CreateCourse;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/courses")
public class CourseController {

    private final CourseService courseService;

    @GetMapping("/{courseId}/exists")
    public ResponseEntity<Boolean> existsById(@PathVariable("courseId") String courseIdStr){

        UUID courseId = UUID.fromString(courseIdStr);

        boolean doesExist = courseService.existsById(courseId);

        return ResponseEntity.ok(doesExist);
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<CourseEntity> getById(@PathVariable("courseId") String courseIdStr){

        UUID courseId = UUID.fromString(courseIdStr);

        CourseEntity gotCourse = courseService.getById(courseId);

        return ResponseEntity.ok(gotCourse);
    }

    @GetMapping
    public ResponseEntity<List<CourseEntity>> getAll(){

        List<CourseEntity> gotCourses = courseService.getAll();

        return ResponseEntity.ok(gotCourses);
    }

    @GetMapping("/ids")
    public ResponseEntity<List<CourseEntity>> getByIds(@RequestParam(required = true, name = "ids[]") String[] idsStrs){

        List<UUID> ids = Arrays.stream(idsStrs)
            .map(UUID::fromString)
            .collect(Collectors.toList());

        List<CourseEntity> gotCourses = courseService.getByIds(ids);

        return ResponseEntity.ok(gotCourses);
    }

    @PostMapping
    public ResponseEntity<CourseEntity> create(@Valid @RequestBody CreateCourse createCourse){

        CourseEntity createdCourse = courseService.create(createCourse.name());

        return ResponseEntity.status(HttpStatus.CREATED).body(createdCourse);
    }

    @DeleteMapping("/{courseId}")
    public ResponseEntity<Void> deleteById(@PathVariable("courseId") String courseIdStr){

        UUID courseId = UUID.fromString(courseIdStr);

        courseService.deleteById(courseId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
