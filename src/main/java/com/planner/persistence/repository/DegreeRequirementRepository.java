package com.planner.persistence.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.planner.data.entities.DegreeRequirement;

@Repository
public interface DegreeRequirementRepository extends JpaRepository<DegreeRequirement, String> {
    Optional<DegreeRequirement> findByProgram(String program);
}