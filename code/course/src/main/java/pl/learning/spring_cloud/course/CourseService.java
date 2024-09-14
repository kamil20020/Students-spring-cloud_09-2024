package pl.learning.spring_cloud.course;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.learning.spring_cloud.course.exception.ConflictException;
import pl.learning.spring_cloud.course.models.CourseEntity;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;

    public boolean existsById(UUID id){

        log.info("Exists by id " + id);

        return courseRepository.existsById(id);
    }

    public CourseEntity getById(UUID id) throws EntityNotFoundException {

        log.info("Get by id " + id);

        return courseRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Course with given id does not exist"));
    }

    public List<CourseEntity> getAll(){

        log.info("Get all");

        return courseRepository.findAll();
    }

    public List<CourseEntity> getByIds(List<UUID> ids){

        log.info("Get by ids " + ids);

        return courseRepository.findAllById(ids);
    }

    public CourseEntity create(String name) throws ConflictException {

        if(courseRepository.existsByName(name)){

            log.error("Conflict name");

            throw new ConflictException("Course with given name already exists");
        }

        log.info("Create");

        return courseRepository.save(
            CourseEntity.builder()
                .name(name)
                .build()
        );
    }

    public void deleteById(UUID id) throws EntityNotFoundException {

        if(!existsById(id)){

            log.error("Does not exist " + id);

            throw new EntityNotFoundException("Course with given name does not exist");
        }

        log.info("Delete by id " + id);

        courseRepository.deleteById(id);
    }
}
