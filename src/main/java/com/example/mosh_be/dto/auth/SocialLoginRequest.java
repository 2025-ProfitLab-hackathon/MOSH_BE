package com.example.mosh_be.dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SocialLoginRequest {
    @NotBlank
    private String providerUid;
    private String accessToken;
    private String nickname;
    private String phoneNumber;
    private LocalDate birthday;
    private String sex;
    private String imageUrl;
}
