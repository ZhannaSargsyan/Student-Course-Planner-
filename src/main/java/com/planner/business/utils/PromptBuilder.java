package com.planner.business.utils;

import com.planner.api.dto.PlanRequest;
import com.planner.data.entities.Course;
import java.util.List;

public class PromptBuilder {

    public static String buildPrompt(PlanRequest preferences, List<Course> courses, String degreeRequirementSummary) {
        StringBuilder sb = new StringBuilder();

        sb.append("Please suggest a suitable course plan for a student based on this information.\n");

        if (preferences.getDegreeProgram() != null && !preferences.getDegreeProgram().isEmpty()) {
            sb.append("Student program: ").append(preferences.getDegreeProgram()).append("\n");

            if (degreeRequirementSummary != null && !degreeRequirementSummary.isEmpty()) {
                sb.append("Degree requirements for the course are: \n").append(degreeRequirementSummary).append("\n");
            }
        }

        if (preferences.getAcademicInterests() != null && !preferences.getAcademicInterests().isEmpty()) {
            sb.append("Interests: ").append(String.join(", ", preferences.getAcademicInterests())).append("\n");
        }

        if (preferences.getPreferredWorkload() != null && !preferences.getPreferredWorkload().isEmpty()) {
            sb.append("Preferred workload: ").append(preferences.getPreferredWorkload()).append("\n");
        }

        if (preferences.getWeeklyAvailability() != null && !preferences.getWeeklyAvailability().isEmpty()) {
            sb.append("Weekly Availability: ").append(preferences.getWeeklyAvailability()).append("\n");
        }

        sb.append("\nAvailable courses:\n");
        for (Course c : courses) {
            sb.append("- ").append(c.getCode()).append(": ").append(c.getTitle())
                    .append(" (").append(c.getCredits()).append(" credits)\n");
        }

        return sb.toString();
    }
}
