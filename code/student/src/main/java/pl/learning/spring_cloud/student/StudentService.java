package pl.learning.spring_cloud.student;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    public boolean existsById(UUID id){

        log.info("Exists by id " + id);

        return studentRepository.existsById(id);
    }

    public StudentEntity getById(UUID id) throws EntityNotFoundException{

        log.info("Get by id" + id);

        return studentRepository.findById(id)
            .orElseThrow(EntityNotFoundException::new);
    }

    public List<StudentEntity> getByIds(List<UUID> ids){

        log.info("Get by ids " + ids.toString());

        return studentRepository.findAllById(ids);
    }

    public List<StudentEntity> getAll(){

        log.info("Get all");

        return studentRepository.findAll();
    }

    public StudentEntity create(StudentEntity student){

        log.info("Create student " + student.toString());

        return studentRepository.save(
            student
        );
    }

    public void deleteById(UUID id) throws EntityNotFoundException{

        if(!studentRepository.existsById(id)){

            log.error("Student does not exist " + id);

            throw new EntityNotFoundException();
        }

        studentRepository.deleteById(id);

        log.info("Student was deleted " + id);
    }
}
