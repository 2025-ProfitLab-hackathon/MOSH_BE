package com.example.mosh_be.dto.auth;

import com.example.mosh_be.dto.user.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class AuthTokenResponse {
    private String tokenType;
    private String accessToken;
    private Integer expiresInSeconds;
    private UserResponse user;
}
