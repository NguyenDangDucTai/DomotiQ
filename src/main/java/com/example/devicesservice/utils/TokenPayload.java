package com.example.devicesservice.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class TokenPayload {

    private String tokenId;
    private String userId;
    private TokenType type;

}
