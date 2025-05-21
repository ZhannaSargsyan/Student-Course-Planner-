package com.planner.business.services.implementation;

import com.planner.business.services.IChatService;
import com.planner.business.models.ChatSession;

import com.planner.business.services.IPromptGeneratorService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.planner.api.dto.PlanRequest;
import com.planner.business.dto.MessageDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.google.genai.Client;
import com.google.genai.types.Content;
import com.google.genai.types.GenerateContentResponse;
import com.google.genai.types.Part;

@Service
public class ChatService  implements IChatService {
    private final Client genAIClient;
    private final JwtService jwtService;
    private final IPromptGeneratorService promptGeneratorService;

    @Value("${genai.model}")
    private String modelName;

    private Map<String, ChatSession> sessions = new ConcurrentHashMap<>();

    public ChatService(Client genAIClient, JwtService jwtService, IPromptGeneratorService promptGeneratorService) {
        this.genAIClient = genAIClient;
        this.jwtService = jwtService;
        this.promptGeneratorService = promptGeneratorService;
    }

    public List<MessageDTO> getChatHistory(String sessionId) {
        ChatSession session = sessions.get(sessionId);
        if (session == null) {
            throw new IllegalStateException("Session not found");
        }

        List<MessageDTO> messages = session.getMessages();
        if (!messages.isEmpty()) {
            messages = messages.subList(1, messages.size());
        }

        return messages;
    }

    public String startNewSession() {
        ChatSession session = new ChatSession();

        sessions.put(session.getId(), session);
        return this.jwtService.generateToken(session.getId());
    }

    public String requestInitialPlan(String sessionId, PlanRequest request) {
        ChatSession session = sessions.get(sessionId);
        if (session == null) {
            throw new IllegalStateException("Session not found");
        }

        String initialPrompt = promptGeneratorService.generatePrompt(request);
        String planResponse = this.askModel(session.getId(), initialPrompt);

        return planResponse;
    }

    public String sendMessage(String sessionId, String message) {
        ChatSession session = sessions.get(sessionId);
        if (session == null) {
            throw new IllegalStateException("Session not found");
        }

        String assistantResponse = this.askModel(sessionId, message);

        return assistantResponse;
    }

    public String askModel(String sessionId, String message) {
        ChatSession session = sessions.get(sessionId);
        if (session == null) {
            throw new IllegalStateException("Session not found");
        }

        session.addMessage(new MessageDTO("user", message));

        List<Content> contents = new ArrayList<>();

        for (MessageDTO msg : session.getMessages()) {
            Content content = Content.builder()
                .role(msg.getRole())
                .parts(List.of(Part.builder()
                    .text(msg.getContent())
                    .build()))
                .build();
            contents.add(content);
        }

        GenerateContentResponse response = genAIClient.models
            .generateContent(modelName, contents, null);

        String assistantResponse = response.text();
        session.addMessage(new MessageDTO("model", assistantResponse));

        return assistantResponse;
    }

    public void endSession(String sessionId) {
        sessions.remove(sessionId);
    }

    @Scheduled(fixedRate = 60 * 60 * 1000) // check every hour for inactive sessions
    public void cleanupInactiveSessions() {
        LocalDateTime cutoff = LocalDateTime.now().minusHours(24);
        sessions.entrySet().removeIf(entry -> 
            entry.getValue().getLastActivity().isBefore(cutoff));
    }
} 
