package com.planner.business.services.implementation;

import com.planner.api.dto.PlanRequest;
import com.planner.business.services.ICoursePlannerService;
import com.planner.business.services.ICourseScraper;
import com.planner.business.utils.PromptBuilder;
import com.planner.data.entities.Course;
import com.planner.data.entities.DegreeRequirement;
import com.planner.persistence.dto.CourseFilter;
import com.planner.persistence.services.ICoursePersistenceService;
import com.planner.persistence.repository.DegreeRequirementRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CoursePlannerService implements ICoursePlannerService {
    private final ICoursePersistenceService coursePersistenceService;
    private final DegreeRequirementRepository degreeRequirementRepository;
    private final ICourseScraper courseScraper;


    public CoursePlannerService(ICoursePersistenceService coursePersistenceService,
                                DegreeRequirementRepository degreeRequirementRepository,
                                ICourseScraper courseScraper) {
        this.coursePersistenceService = coursePersistenceService;
        this.degreeRequirementRepository = degreeRequirementRepository;
        this.courseScraper = courseScraper;
    }

    @Override
    public String generatePlan(PlanRequest request) {
        CourseFilter filter = new CourseFilter();
        filter.setProgram(request.getDegreeProgram());

        long totalCourses = coursePersistenceService.count();
        if (totalCourses == 0) {
            List<Course> scraped = courseScraper.scrapeCourses();
            coursePersistenceService.upsertAll(scraped);
        }

        List<Course> availableCourses = coursePersistenceService.findByFilter(filter);

        if (availableCourses.isEmpty()) {
            availableCourses = coursePersistenceService.findAll();
        }

        Optional<DegreeRequirement> requirementOpt = degreeRequirementRepository.findByProgram(request.getDegreeProgram());
        String programSummary = requirementOpt.map(DegreeRequirement::getSummary).orElse("N/A");

        String prompt = PromptBuilder.buildPrompt(request, availableCourses, programSummary);

        return prompt;
    }
}
