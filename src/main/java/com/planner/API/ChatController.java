package com.planner.API;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.planner.Business.Services.Implementation.ChatService;
import com.planner.Business.Services.Implementation.JwtService;
import com.planner.Data.DTO.SessionRequest;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;
    private final JwtService jwtService;

    @PostMapping("/session")
    public ResponseEntity<String> startSession(@RequestBody SessionRequest request) {
        String token = chatService.startSession(request);
        return ResponseEntity.ok(token);
    }

    @GetMapping("/session")
    public ResponseEntity<String> getSession(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        if (!jwtService.isTokenValid(token)) {
            return ResponseEntity.status(401).body("Invalid or expired token");
        }

        String sessionId = jwtService.extractSessionId(token);
        String[] session = chatService.getChatHistory(sessionId);
        StringBuilder response = new StringBuilder();
        for (String message : session) {
            response.append(message).append("\n");
        }
        return ResponseEntity.ok(response.toString());
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
} 