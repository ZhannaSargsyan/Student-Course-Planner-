package com.planner.business.services;

import com.planner.business.dto.PlanRequest;

public interface IPromptGeneratorService {
    String generatePrompt(PlanRequest request);
}
