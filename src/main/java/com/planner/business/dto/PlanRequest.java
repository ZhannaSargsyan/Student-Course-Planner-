package com.planner.business.dto;

import java.util.List;

/**
 * DTO for receiving student course planning request data from the frontend.
 */
public class PlanRequest {
    private String firstName;
    private String lastName;
    private String studentId;
    private String degreeProgram;
    private String preferredWorkload;
    private String academicInterests;
    private String weeklyAvailability;
    private double desiredCredits;
    private List<String> takenCourses;

    // Getters and setters for all fields
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public String getDegreeProgram() { return degreeProgram; }
    public void setDegreeProgram(String degreeProgram) { this.degreeProgram = degreeProgram; }

    public String getPreferredWorkload() { return preferredWorkload; }
    public void setPreferredWorkload(String preferredWorkload) { this.preferredWorkload = preferredWorkload; }

    public String getAcademicInterests() { return academicInterests; }
    public void setAcademicInterests(String academicInterests) { this.academicInterests = academicInterests; }

    public String getWeeklyAvailability() { return weeklyAvailability; }
    public void setWeeklyAvailability(String weeklyAvailability) { this.weeklyAvailability = weeklyAvailability; }

    public double getDesiredCredits() {
        return desiredCredits;
    }
    
    public void setDesiredCredits(double desiredCredits) {
        this.desiredCredits = desiredCredits;
    }
    
    public List<String> getTakenCourses() {
        return takenCourses;
    }
    
    public void setTakenCourses(List<String> takenCourses) {
        this.takenCourses = takenCourses;
    }
}
