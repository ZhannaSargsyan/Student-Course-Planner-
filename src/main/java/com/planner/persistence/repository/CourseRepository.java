package com.planner.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.planner.data_temp.entities_temp.Course;

import java.util.Optional;
@Repository
public interface CourseRepository extends JpaRepository<Course, String>, JpaSpecificationExecutor<Course> {
    Optional<Course> findByProgramAndCode(String program, String code);
}