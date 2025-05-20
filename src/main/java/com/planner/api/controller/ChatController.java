package com.planner.api.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.planner.api.dto.PlanRequest;
import com.planner.business.dto.MessageDTO;
import com.planner.business.dto.MessageRequest;
import com.planner.business.services.implementation.ChatService;
import com.planner.business.services.implementation.JwtService;

@RestController
@RequestMapping("/api")
public class ChatController {
    private final ChatService chatService;
    private final JwtService jwtService;

    public ChatController(ChatService chatService, JwtService jwtService) {
        this.chatService = chatService;
        this.jwtService = jwtService;
    }

    @PostMapping("/session")
    public ResponseEntity<String> startSession() {
        String token = chatService.startNewSession();
        return ResponseEntity.ok(token);
    }

    @DeleteMapping("/session")
    public ResponseEntity<Void> endSession(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        if (!jwtService.isTokenValid(token)) {
            return ResponseEntity.status(401).build();
        }

        String sessionId = jwtService.extractSessionId(token);
        chatService.endSession(sessionId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/generate-plan")
    public ResponseEntity<String> generatePlan(
        @RequestHeader("Authorization") String authHeader,
        @RequestBody PlanRequest request
    ) { 
        String token = authHeader.replace("Bearer ", "");
        if (!jwtService.isTokenValid(token)) {
            return ResponseEntity.status(401).body("Invalid or expired token");
        }

        String sessionId = jwtService.extractSessionId(token);
        String response = chatService.requestInitialPlan(sessionId, request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/messages")
    public ResponseEntity<String> sendMessage(
        @RequestHeader("Authorization") String authHeader,
        @RequestBody MessageRequest request) {
        
        String token = authHeader.replace("Bearer ", "");
        if (!jwtService.isTokenValid(token)) {
            return ResponseEntity.status(401).body("Invalid or expired token");
        }

        String sessionId = jwtService.extractSessionId(token);
        String response = chatService.sendMessage(sessionId, request.getMessage());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/messages")
    public ResponseEntity<?> getSession(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        if (!jwtService.isTokenValid(token)) {
            return ResponseEntity.status(401).body("Invalid or expired token");
        }

        String sessionId = jwtService.extractSessionId(token);
        List<MessageDTO> chatHistory = chatService.getChatHistory(sessionId);
        
        if (chatHistory.isEmpty()) {
            return ResponseEntity.ok("No chat history available");
        }
        return ResponseEntity.ok(chatHistory);
    }
} 