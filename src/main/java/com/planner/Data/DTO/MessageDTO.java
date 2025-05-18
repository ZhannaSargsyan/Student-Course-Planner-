package com.planner.Data.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO {
    private String role; // "user" or "assistant"
    private String content;


    public String getRole() {
        return role;
    }

    public String getContent() {
        return content;
    }
} 