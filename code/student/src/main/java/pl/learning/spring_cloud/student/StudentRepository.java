package pl.learning.spring_cloud.student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.learning.spring_cloud.student.models.StudentEntity;

import java.util.UUID;

@Repository
public interface StudentRepository extends JpaRepository <StudentEntity, UUID> {

}
