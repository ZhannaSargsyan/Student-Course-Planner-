package com.planner.business.services;

import com.planner.business.dto.PlanRequest;
import com.planner.business.dto.MessageDTO;

import java.util.List;

public interface IChatService {
    String startNewSession();
    List<MessageDTO> getChatHistory(String sessionId);
    String sendMessage(String sessionId, String message);
    String requestInitialPlan(String sessionId, PlanRequest request);
    void endSession(String sessionId);
}
