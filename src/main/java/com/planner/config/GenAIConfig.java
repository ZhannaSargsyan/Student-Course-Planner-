package com.planner.Config;

import com.google.genai.Client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GenAIConfig {

    @Value("${genai.api.key}")
    private String apiKey;

    @Bean
    public Client genAIClient() {
        return Client.builder()
            .apiKey(apiKey)
            .build();
    }
} 
