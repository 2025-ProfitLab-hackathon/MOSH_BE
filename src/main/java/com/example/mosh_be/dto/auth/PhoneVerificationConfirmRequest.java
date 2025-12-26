package com.example.mosh_be.dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PhoneVerificationConfirmRequest {
    @NotBlank
    private String verificationId;
    @NotBlank
    private String code;
}
