package com.planner.Data.Repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.planner.Data.Entities.DegreeRequirement;

@Repository
public interface DegreeRequirementRepository extends JpaRepository<DegreeRequirement, String> {
    Optional<DegreeRequirement> findByProgram(String program);
}