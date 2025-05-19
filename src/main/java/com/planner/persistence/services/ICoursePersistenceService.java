package com.planner.persistence.services;

import java.util.List;

import com.planner.data_temp.entities_temp.Course;
import com.planner.persistence.dto.CourseFilter;

public interface ICoursePersistenceService {

    public void upsertAll(List<Course> courses);

    public List<Course> findByFilter(CourseFilter filter);
}
