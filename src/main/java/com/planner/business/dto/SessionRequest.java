package com.planner.business.dto;

import lombok.Data;

@Data
public class SessionRequest {
    private String degree;
    private String profession;

    public String getDegree() {
        return degree;
    }

    public String getProfession() {
        return profession;
    }
} 