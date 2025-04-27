package Persistence.Services;

import Data.Entities.Course;
import java.util.List;

public interface ICoursePersistenceService {

    public void upsertAll(List<Course> courses);

}
