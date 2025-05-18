package com.planner.Business.Services.Implementation;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import com.planner.Business.Services.ICoursePersistenceService;
import com.planner.Data.DTO.CourseFilter;
import com.planner.Data.Entities.Course;
import com.planner.Data.Repository.CourseRepository;
import com.planner.Data.Repository.CourseSpecifications;


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
        return repo.findAll(CourseSpecifications.withFilter(filter));
    }
}
