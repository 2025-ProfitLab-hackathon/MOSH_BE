package com.example.mosh_be.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PhoneVerificationSendRequest {
    @NotBlank
    @Pattern(regexp = "^010\\d{8}$", message = "Invalid phone number format")
    private String phoneNumber;
}
