package com.planner.business.services;

import com.planner.api.dto.PlanRequest;

public interface IPromptGeneratorService {
    String generatePlan(PlanRequest request);
}
