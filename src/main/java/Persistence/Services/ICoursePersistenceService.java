package Persistence.Services;

import Persistence.DTO.CourseFilter;
import Data.Entities.Course;
import java.util.List;

public interface ICoursePersistenceService {

    public void upsertAll(List<Course> courses);

    public List<Course> findByFilter(CourseFilter filter);
}
