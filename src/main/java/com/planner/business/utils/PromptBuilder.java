package com.planner.business.utils;

import com.planner.business.dto.PlanRequest;
import com.planner.data.entities.Course;
import java.util.List;

public class PromptBuilder {

    public static String buildPrompt(PlanRequest preferences, List<Course> courses, String degreeRequirementSummary) {
        StringBuilder sb = new StringBuilder();

        sb.append("You are a smart university course planner assistant for AUA students. Your job is to recommend an optimized semester course plan based on the student's profile.\n");
        sb.append("Follow these rules:\n\n");
        sb.append("- Only recommend courses that match the student's degree curriculum.\n");
        sb.append("- Respect course prerequisites: if a course has prerequisites not completed yet, do not suggest it.\n");
        sb.append("- Personalize the schedule based on the student's preferences:\n");
        sb.append("    - Preferred workload (light, normal, heavy)\n");
        sb.append("    - Topics of interest\n");
        sb.append("    - Time availability (e.g., only morning classes)\n");
        sb.append("- Recommend between 3 to 6 courses depending on workload.\n");
        sb.append("- Prioritize required core courses if available, but allow electives if needed.\n");
        sb.append("- Suggest a balanced schedule, mixing easy and difficult courses when possible.\n");
        sb.append("- Please note that course schedule is not available, don't ask for it.\n");
        sb.append("- Concentrate on course recommendations, if user asks questions around unrelated topics please advise them to stick to course recommendations.\n");
        sb.append("- Don't change the topic to something else.\n");
        sb.append("Return your recommendation as a list of courses with a short explanation for each choice.\n");
        sb.append("Make your answer polite, friendly, dialogue towards student it will be forwarded to him without any change.\n");
        sb.append("Here is the all information about the student\n");

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

        if (preferences.getDesiredCredits() != 0) {
            sb.append("Desired Credits: ").append(preferences.getDesiredCredits()).append("\n");
        }

        if (preferences.getTakenCourses() != null && !preferences.getTakenCourses().isEmpty()) {
            sb.append("Already taken courses: ").append(String.join(", ", preferences.getTakenCourses())).append("\n");
        }

        sb.append("\nAvailable courses:\n");
        for (Course c : courses) {
            sb
                .append("- ")
                .append(c.getCode())
                .append(": ")
                .append(c.getTitle())
                .append(" (").append(c.getCredits()).append(" credits)")
                .append("Prerequisite: ").append(c.getPrerequisite())
                .append("\n");
        }

        return sb.toString();
    }
}
