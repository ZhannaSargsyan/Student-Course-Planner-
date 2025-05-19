package com.planner.business.services.implementation;

import com.planner.api.dto.PlanRequest;
import com.planner.business.services.ICoursePlannerService;
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

    public CoursePlannerService(ICoursePersistenceService coursePersistenceService, DegreeRequirementRepository degreeRequirementRepository) {
        this.coursePersistenceService = coursePersistenceService;
        this.degreeRequirementRepository = degreeRequirementRepository;
    }

    @Override
    public String generatePlan(PlanRequest request) {
        CourseFilter filter = new CourseFilter();
        filter.setProgram(request.getDegreeProgram());

        List<Course> availableCourses = coursePersistenceService.findByFilter(filter);

        Optional<DegreeRequirement> requirementOpt = degreeRequirementRepository.findByProgram(request.getDegreeProgram());
        String programSummary = requirementOpt.map(DegreeRequirement::getSummary).orElse("N/A");

        String prompt = PromptBuilder.buildPrompt(request, availableCourses, programSummary);

        return prompt;
    }
}
