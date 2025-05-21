package com.planner.business.services.implementation;

import com.planner.business.dto.PlanRequest;
import com.planner.business.services.IPromptGeneratorService;
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
public class PromptGeneratorService implements IPromptGeneratorService {
    private final ICoursePersistenceService coursePersistenceService;
    private final DegreeRequirementRepository degreeRequirementRepository;

    public PromptGeneratorService(ICoursePersistenceService coursePersistenceService,
                                  DegreeRequirementRepository degreeRequirementRepository) {
        this.coursePersistenceService = coursePersistenceService;
        this.degreeRequirementRepository = degreeRequirementRepository;
    }

    @Override
    public String generatePrompt(PlanRequest request) {
        CourseFilter filter = new CourseFilter();

        filter.setExcludedCourseCodes(request.getTakenCourses());

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
