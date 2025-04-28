package Persistence.Repository;
import Data.Entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface CourseRepository extends JpaRepository<Course, String> {
    Optional<Course> findByProgramAndCode(String program, String code);
}