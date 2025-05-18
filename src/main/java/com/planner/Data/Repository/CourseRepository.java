package com.planner.Data.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import com.planner.Data.Entities.Course;
@Repository
public interface CourseRepository extends JpaRepository<Course, String> {
    Optional<Course> findByProgramAndCode(String program, String code);
}