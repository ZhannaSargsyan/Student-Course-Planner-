package com.planner.persistence.services;

import java.util.List;

import com.planner.data.entities.Course;
import com.planner.persistence.dto.CourseFilter;

public interface ICoursePersistenceService {

   public long count();

    public void upsertAll(List<Course> courses);

    public List<Course> findAll();

    public List<Course> findByFilter(CourseFilter filter);
}
