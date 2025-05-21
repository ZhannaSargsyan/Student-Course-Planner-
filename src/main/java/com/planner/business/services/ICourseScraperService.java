package com.planner.business.services;

import java.util.List;

import com.planner.data.entities.Course;

public interface ICourseScraperService {
    public List<Course> scrapeCourses();
}
