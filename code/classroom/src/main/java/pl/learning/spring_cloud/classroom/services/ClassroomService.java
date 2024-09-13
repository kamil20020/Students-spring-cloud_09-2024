package pl.learning.spring_cloud.classroom.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.learning.spring_cloud.classroom.ClassroomRepository;
import pl.learning.spring_cloud.classroom.models.ClassroomEntity;
import pl.learning.spring_cloud.classroom.models.ClassroomHeader;
import pl.learning.spring_cloud.classroom.models.Course;
import pl.learning.spring_cloud.classroom.models.Student;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ClassroomService {

    private final ClassroomRepository classroomRepository;

    private final StudentService studentService;

    public ClassroomEntity getClassById(UUID classId) throws EntityNotFoundException {

        log.info("Get class by id" + classId);

        return classroomRepository.findById(classId)
            .orElseThrow(EntityNotFoundException::new);
    }

    public List<ClassroomEntity> getAll(){

        log.info("Get all classes");

        return classroomRepository.findAll();
    }

    public List<Course> getCoursesByStudentId(UUID studentId){

        Set<UUID> studentIds = Set.of(studentId);
//        List<UUID> coursesIds = classroomRepository.findByStudentsIds(studentIds).stream()
//            .map(classroom -> {
//                return null;
//            });

        return List.of();
    }

    public List<ClassroomEntity> getStudentClasses(UUID studentId){

        Set<UUID> studentIds = Set.of(studentId);

        log.info("Get student classes");

        return classroomRepository.findByStudentsIdsContains(studentIds);
    }

    public List<Student> getClassStudents(UUID classId) throws EntityNotFoundException {

        ClassroomEntity foundClassroom = classroomRepository.findById(classId)
            .orElseThrow(EntityNotFoundException::new);

        log.info("Found classroom" + foundClassroom.toString());

        Set<UUID> foundClassStudentsIds = foundClassroom.getStudentsIds();

        return foundClassStudentsIds.stream()
            .map(studentService::getById)
            .collect(Collectors.toList());
    }

    public ClassroomEntity createClass(UUID courseId){

        ClassroomEntity toCreateClassroom = ClassroomEntity.builder()
            .courseId(courseId)
            .studentsIds(new HashSet<>())
            .build();

        log.info("Create classroom" + toCreateClassroom.toString());

        return classroomRepository.save(toCreateClassroom);
    }

    @Transactional
    public Student addStudentToClass(UUID studentId, UUID classId) throws EntityNotFoundException {

        ClassroomEntity foundClassroom = classroomRepository.findById(classId)
            .orElseThrow(EntityNotFoundException::new);

        log.info("Found classroom" + foundClassroom.toString());

        foundClassroom.getStudentsIds().add(studentId);

        log.info("Added student" + studentId);

        return studentService.getById(studentId);
    }

    @Transactional
    public void removeStudentFromClass(UUID studentId, UUID classId) throws EntityNotFoundException {

        ClassroomEntity foundClassroom = classroomRepository.findById(classId)
            .orElseThrow(EntityNotFoundException::new);

        log.info("Found classroom" + foundClassroom.toString());

        foundClassroom.getStudentsIds().remove(studentId);

        log.info("Removed student" + studentId);
    }

    public void removeClassById(UUID classId) throws EntityNotFoundException {

        if(!classroomRepository.existsById(classId)){

            log.error("Classrom does not exist" + classId);

            throw new EntityNotFoundException();
        }

        classroomRepository.deleteById(classId);

        log.error("Classrom was deleted" + classId);
    }
}
