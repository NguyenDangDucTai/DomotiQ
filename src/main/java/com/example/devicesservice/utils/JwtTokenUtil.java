package com.example.devicesservice.utils;

import com.example.devicesservice.models.User;
import org.springframework.stereotype.Service;

@Service
public interface JwtTokenUtil {

    String generateToken(User user, TokenType type, long expirationTime);
    TokenPayload validateToken(String token, TokenType type);
    TokenPayload getTokenPayload(String token);

}
