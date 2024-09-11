package pl.learning.spring_cloud.classroom;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.learning.spring_cloud.classroom.models.ClassroomEntity;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Repository
public interface ClassroomRepository extends JpaRepository <ClassroomEntity, UUID> {

    List<ClassroomEntity> findByStudentsIds(Set<UUID> studentIds);
}
