package com.planner.Business.Services.Implementation;

import lombok.RequiredArgsConstructor;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import com.planner.Data.DTO.MessageDTO;
import com.planner.Data.DTO.SessionRequest;


@Service
@RequiredArgsConstructor
public class ChatService {
    private final JwtService jwtService;
    private Map<String, ChatSession> sessions = new ConcurrentHashMap<>();

    public String[] getChatHistory(String sessionId) {
        ChatSession session = sessions.get(sessionId);
        if (session == null) {
            throw new IllegalStateException("Session not found");
        }

        return session.getMessages().stream()
            .filter(message -> !message.getRole().equals("system"))
            .map(MessageDTO::getContent)
            .collect(Collectors.toList())
            .toArray(new String[0]);
    }

    public String startSession(SessionRequest request) {
        String sessionId = UUID.randomUUID().toString();
        String initialPrompt = String.format(
            "You are a course planning assistant. The user has a %s degree and works as a %s. " +
            "Help them plan their educational journey and provide relevant course recommendations. " +
            "Be concise but informative in your responses.",
            request.getDegree(), request.getProfession()
        );

        ChatSession session = new ChatSession(
            sessionId,
            request.getDegree(),
            request.getProfession(),
            LocalDateTime.now()
        );
        
        // Add the initial interaction to chat history
        session.addMessage(new MessageDTO("system", initialPrompt));
        session.addMessage(new MessageDTO("assistant", "Welcome! How can I assist you today?"));
        
        sessions.put(sessionId, session);
        return this.jwtService.generateToken(sessionId);
    }

    public void endSession(String sessionId) {
        sessions.remove(sessionId);
    }

    private static class ChatSession {
        private final String id;
        private final String degree;
        private final String profession;
        private LocalDateTime lastActivity;
        private final List<MessageDTO> messages;

        public ChatSession(String id, String degree, String profession, LocalDateTime lastActivity) {
            this.id = id;
            this.degree = degree;
            this.profession = profession;
            this.lastActivity = lastActivity;
            this.messages = new ArrayList<>();
        }

        public LocalDateTime getLastActivity() {
            return lastActivity;
        }

        public void setLastActivity(LocalDateTime lastActivity) {
            this.lastActivity = lastActivity;
        }

        public List<MessageDTO> getMessages() {
            return messages;
        }

        public void addMessage(MessageDTO message) {
            messages.add(message);
        }
    }
} 