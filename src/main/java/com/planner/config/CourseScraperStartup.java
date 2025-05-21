package com.planner.config;

import com.planner.business.services.ICourseScraperService;
import com.planner.data.entities.Course;
import com.planner.persistence.services.ICoursePersistenceService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class CourseScraperStartup implements CommandLineRunner {

    private final ICourseScraperService courseScraper;
    private final ICoursePersistenceService coursePersistenceService;

    public CourseScraperStartup(ICourseScraperService courseScraper, ICoursePersistenceService coursePersistenceService) {
        this.courseScraper = courseScraper;
        this.coursePersistenceService = coursePersistenceService;
    }

    @Override
    public void run(String... args) {
        long courseCount = coursePersistenceService.count();

        if (courseCount == 0) {
            List<Course> scraped = courseScraper.scrapeCourses();
            coursePersistenceService.upsertAll(scraped);
        }
    }
}

