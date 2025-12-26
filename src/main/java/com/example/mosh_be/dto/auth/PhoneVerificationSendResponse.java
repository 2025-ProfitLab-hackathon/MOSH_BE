package com.example.mosh_be.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class PhoneVerificationSendResponse {
    private String verificationId;
    private Integer expiresInSeconds;
}
