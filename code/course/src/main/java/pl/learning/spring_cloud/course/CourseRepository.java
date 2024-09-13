package pl.learning.spring_cloud.course;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.learning.spring_cloud.course.models.CourseEntity;

import java.util.UUID;

@Repository
public interface CourseRepository extends JpaRepository <CourseEntity, UUID> {

    boolean existsByName(String name);
}
