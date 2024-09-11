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

        return classroomRepository.findById(classId)
            .orElseThrow(EntityNotFoundException::new);
    }

    private List<ClassroomHeader> map(List<ClassroomEntity> classes){

        return classes.stream()
            .map(classroom -> {

                Course foundCourse;

                return new ClassroomHeader(classroom.getId(), null);
            })
            .toList();
    }

    public List<ClassroomHeader> getClasses(){

        List<ClassroomEntity> foundClasses = classroomRepository.findAll();

        return map(foundClasses);
    }

    public List<Course> getCoursesByStudentId(UUID studentId){

        Set<UUID> studentIds = Set.of(studentId);
//        List<UUID> coursesIds = classroomRepository.findByStudentsIds(studentIds).stream()
//            .map(classroom -> {
//                return null;
//            });

        return List.of();
    }

    public List<ClassroomHeader> getStudentClasses(UUID studentId){

        Set<UUID> studentIds = Set.of(studentId);
        List<ClassroomEntity> foundClasses = classroomRepository.findByStudentsIds(studentIds);

        return map(foundClasses);
    }

    public List<Student> getClassStudents(UUID classId) throws EntityNotFoundException {

        ClassroomEntity foundClassroom = classroomRepository.findById(classId)
            .orElseThrow(EntityNotFoundException::new);

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

        return classroomRepository.save(toCreateClassroom);
    }

    @Transactional
    public ClassroomEntity addStudentToClass(UUID studentId, UUID classId) throws EntityNotFoundException {

        ClassroomEntity foundClassroom = classroomRepository.findById(classId)
            .orElseThrow(EntityNotFoundException::new);

        foundClassroom.getStudentsIds().add(studentId);

        return foundClassroom;
    }

    @Transactional
    public void removeStudentFromClass(UUID studentId, UUID classId) throws EntityNotFoundException {

        ClassroomEntity foundClassroom = classroomRepository.findById(classId)
            .orElseThrow(EntityNotFoundException::new);

        foundClassroom.getStudentsIds().remove(studentId);
    }

    public void removeClassById(UUID classId) throws EntityNotFoundException {

        if(!classroomRepository.existsById(classId)){
            throw new EntityNotFoundException();
        }

        classroomRepository.deleteById(classId);
    }
}
