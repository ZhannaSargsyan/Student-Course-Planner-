package com.planner.business.services;

public interface IJwtService {
    String generateToken(String sessionId);
    String extractSessionId(String token);
    <T> T extractClaim(String token, java.util.function.Function<io.jsonwebtoken.Claims, T> claimsResolver);
    boolean isTokenValid(String token);
}
