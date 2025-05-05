package com.planner.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    private String role; // "user" or "assistant"
    private String content;


    public String getRole() {
        return role;
    }

    public String getContent() {
        return content;
    }
} 