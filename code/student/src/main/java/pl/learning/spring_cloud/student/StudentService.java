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

    public StudentEntity getById(UUID id) throws EntityNotFoundException{

        return studentRepository.findById(id)
            .orElseThrow(EntityNotFoundException::new);
    }

    public List<StudentEntity> getAll(){
        return studentRepository.findAll();
    }

    public StudentEntity create(StudentEntity student){
        return studentRepository.save(
            student
        );
    }

    public void deleteById(UUID id) throws EntityNotFoundException{

        if(!studentRepository.existsById(id)){
            throw new EntityNotFoundException();
        }

        studentRepository.deleteById(id);
    }
}
