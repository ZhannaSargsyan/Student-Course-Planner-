package com.planner.api.controller;

import com.planner.api.dto.PlanRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

/**
 * REST controller for handling course planning requests.
 */
@RestController
@RequestMapping("/api/plan")
public class CoursePlannerController {

	/**
	 * Receives a course planning request and returns a placeholder response.
	 *
	 * @param request the plan request data from the frontend
	 * @return a placeholder response string for now
	 */
    @PostMapping("/preview")
    public ResponseEntity<String> preview(@RequestBody PlanRequest request) {
        // Echo back the received data for now
        String message = String.format(
            "Received: %s %s, ID: %s, Degree: %s, Workload: %s, Interests: %s, Availability: %s",
            request.getFirstName(),
            request.getLastName(),
            request.getStudentId(),
            request.getDegreeProgram(),
            request.getPreferredWorkload(),
            request.getAcademicInterests(),
            request.getWeeklyAvailability()
        );

		//TO DO 1: Add preprocessing of the data and prompt generation
		//To Do 2: Store gemini response in the message variable

		//This is a placeholder for the actual logic to generate a course plan
        return ResponseEntity.ok(message);
    }
}
