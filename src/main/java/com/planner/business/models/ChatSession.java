package com.planner.business.models;

import com.planner.business.dto.MessageDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ChatSession {
    private final String id;
    private LocalDateTime lastActivity;
    private final List<MessageDTO> messages;

    public ChatSession() {
        this.id = UUID.randomUUID().toString();
        this.lastActivity = LocalDateTime.now();
        this.messages = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public LocalDateTime getLastActivity() {
        return lastActivity;
    }

    public List<MessageDTO> getMessages() {
        this.lastActivity = LocalDateTime.now();
        return messages;
    }

    public void addMessage(MessageDTO message) {
        this.lastActivity = LocalDateTime.now();
        messages.add(message);
    }
}