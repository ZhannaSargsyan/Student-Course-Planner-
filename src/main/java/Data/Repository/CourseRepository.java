package Repository;
import Data.Entities.Courses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface CourseRepository extends JpaRepository<Courses, String> {
    Optional<Courses> findByProgramAndCode(String program, String code);
}