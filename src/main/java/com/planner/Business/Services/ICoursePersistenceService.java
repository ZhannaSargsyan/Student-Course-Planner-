package com.planner.Business.Services;

import java.util.List;

import com.planner.Data.Entities.Course;

public interface ICoursePersistenceService {

    public void upsertAll(List<Course> courses);

}
