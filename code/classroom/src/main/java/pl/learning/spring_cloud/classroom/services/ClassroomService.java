package pl.learning.spring_cloud.classroom.services;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.learning.spring_cloud.classroom.ClassroomRepository;
import pl.learning.spring_cloud.classroom.config.EventsQueueProducer;
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
    private final CourseService courseService;

    private final EventsQueueProducer eventsQueueProducer;

    public ClassroomEntity getClassById(UUID classId) throws EntityNotFoundException {

        log.info("Get class by id" + classId);

        return classroomRepository.findById(classId)
            .orElseThrow(() -> new EntityNotFoundException("Class with given id does not exist"));
    }

    public List<ClassroomEntity> getAll(){

        log.info("Get all classes");

        return classroomRepository.findAll();
    }

    public List<Course> getCoursesByStudentId(UUID studentId) throws EntityNotFoundException{

        if(!studentService.doesExist(studentId)){
            throw new EntityNotFoundException("Student with given id does not exist");
        }

        Set<UUID> studentIds = Set.of(studentId);

        List<UUID> coursesIds = classroomRepository.findByStudentsIdsContains(studentIds).stream()
            .map(ClassroomEntity::getCourseId)
            .toList();

        return courseService.getByIds(coursesIds);
    }

    public List<ClassroomEntity> getStudentClasses(UUID studentId) throws EntityNotFoundException{

        Set<UUID> studentIds = Set.of(studentId);

        if(!studentService.doesExist(studentId)){
            throw new EntityNotFoundException("Student with given id does not exist");
        }

        log.info("Get student classes");

        return classroomRepository.findByStudentsIdsContains(studentIds);
    }

    public List<Student> getClassStudents(UUID classId) throws EntityNotFoundException {

        ClassroomEntity foundClassroom = classroomRepository.findById(classId)
            .orElseThrow(() -> new EntityNotFoundException("Class with given id does not exist"));

        log.info("Found classroom" + foundClassroom.toString());

        Set<UUID> foundClassStudentsIds = foundClassroom.getStudentsIds();

        return studentService.getByIds(foundClassStudentsIds);
    }

    public ClassroomEntity createClass(UUID courseId) throws EntityNotFoundException{

        if(!courseService.existsById(courseId)){
            throw new EntityNotFoundException("Course with given id does not exist");
        }

        ClassroomEntity toCreateClassroom = ClassroomEntity.builder()
            .courseId(courseId)
            .studentsIds(new HashSet<>())
            .build();

        log.info("Create classroom" + toCreateClassroom.toString());

        ClassroomEntity createdClass = classroomRepository.save(toCreateClassroom);

        eventsQueueProducer.sendMessage("For course " + courseId + " was created class with id " + createdClass.getId());

        return createdClass;
    }

    @Transactional
    public Student addStudentToClass(UUID studentId, UUID classId) throws EntityNotFoundException, EntityExistsException {

        ClassroomEntity foundClassroom = classroomRepository.findById(classId)
            .orElseThrow(() -> new EntityNotFoundException("Class with given id does not exist"));

        log.info("Found classroom" + foundClassroom.toString());

        if(foundClassroom.getStudentsIds().contains(studentId)){
            throw new EntityExistsException("Given student already is in the given class");
        }

        foundClassroom.getStudentsIds().add(studentId);

        log.info("Added student" + studentId);

        eventsQueueProducer.sendMessage("To class " + classId + " was added student " + studentId);

        return studentService.getById(studentId);
    }

    @Transactional
    public void removeStudentFromClass(UUID studentId, UUID classId) throws EntityNotFoundException {

        ClassroomEntity foundClassroom = classroomRepository.findById(classId)
            .orElseThrow(() -> new EntityNotFoundException("Class with given id does not exist"));

        log.info("Found classroom" + foundClassroom.toString());

        if(!foundClassroom.getStudentsIds().contains(studentId)){
            throw new EntityNotFoundException("Given student is not in the given class");
        }

        foundClassroom.getStudentsIds().remove(studentId);

        log.info("Removed student" + studentId);

        eventsQueueProducer.sendMessage("From class " + classId + " was removed student " + studentId);
    }

    public void removeClassById(UUID classId) throws EntityNotFoundException {

        if(!classroomRepository.existsById(classId)){

            log.error("Classrom does not exist" + classId);

            throw new EntityNotFoundException("Class with given id does not exist");
        }

        classroomRepository.deleteById(classId);

        log.error("Classrom was deleted" + classId);

        eventsQueueProducer.sendMessage("Class " + classId + " was removed");
    }
}
