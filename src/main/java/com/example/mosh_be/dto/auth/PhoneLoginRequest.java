package com.example.mosh_be.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PhoneLoginRequest {
    @NotBlank
    private String phoneVerificationToken;
    @NotBlank
    private String nickname;
    @NotNull
    private LocalDate birthday;
    private String sex;
    private String imageUrl;
}
