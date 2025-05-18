package Persistence.Repository;
import Data.Entities.DegreeRequirement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DegreeRequirementRepository extends JpaRepository<DegreeRequirement, String> {
    Optional<DegreeRequirement> findByProgram(String program);
}