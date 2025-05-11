package Persistence.Services.Implementation;

import Persistence.DTO.CourseFilter;
import Data.Entities.Course;
import Persistence.Services.ICoursePersistenceService;
import Persistence.Repository.CourseRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Service
public class CoursePersistenceService implements ICoursePersistenceService {
    private final CourseRepository repo;

    public CoursePersistenceService(CourseRepository repo) {
        this.repo = repo;
    }

    @Override
    @Transactional
    public void upsertAll(List<Course> courses) {
        for (Course c : courses) {
            repo.findByProgramAndCode(c.getProgram(), c.getCode())
                    .map(existing -> {
                        existing.setTitle(c.getTitle());
                        existing.setDescription(c.getDescription());
                        existing.setCredits(c.getCredits());
                        existing.setPrerequisite(c.getPrerequisite());
                        existing.setLastUpdated(Timestamp.from(Instant.now()));
                        return repo.save(existing);
                    })
                    .orElseGet(() -> repo.save(c));
        }
    }

    public List<Course> findByFilter(CourseFilter filter) {
        return repo.findAll(Persistence.Repository.CourseSpecifications.withFilter(filter));
    }
}
