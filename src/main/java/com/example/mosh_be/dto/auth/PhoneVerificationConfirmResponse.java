package com.example.mosh_be.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class PhoneVerificationConfirmResponse {
    private String phoneVerificationToken;
}
