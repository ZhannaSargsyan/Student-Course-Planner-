package com.planner.api.controller;

import com.planner.api.dto.PlanRequest;
import com.planner.business.services.ICoursePlannerService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

/**
 * REST controller for handling course planning requests.
 */
@RestController
@RequestMapping("/api/plan")
public class CoursePlannerController {

    private final ICoursePlannerService coursePlannerService;

    public CoursePlannerController(ICoursePlannerService coursePlannerService) {
        this.coursePlannerService = coursePlannerService;
    }

	/**
	 * Receives a course planning request and returns a placeholder response.
	 *
	 * @param request the plan request data from the frontend
	 * @return a placeholder response string for now
	 */
    @PostMapping("/preview")
    public ResponseEntity<String> preview(@RequestBody PlanRequest request) {
        String prompt = coursePlannerService.generatePlan(request);

        return ResponseEntity.ok(prompt);
    }
}

