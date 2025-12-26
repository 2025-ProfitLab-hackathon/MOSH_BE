package com.example.mosh_be.controller;

import com.example.mosh_be.domain.enums.SocialProvider;
import com.example.mosh_be.dto.auth.*;
import com.example.mosh_be.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/social/{provider}/login")
    public ResponseEntity<AuthTokenResponse> socialLogin(
            @PathVariable String provider,
            @Valid @RequestBody SocialLoginRequest request
    ) {
        SocialProvider socialProvider = SocialProvider.valueOf(provider.toUpperCase());
        AuthTokenResponse response = authService.socialLogin(socialProvider, request);

        HttpStatus status = response.getUser().getCreatedAt().equals(response.getUser().getUpdatedAt())
                ? HttpStatus.CREATED
                : HttpStatus.OK;

        return ResponseEntity.status(status).body(response);
    }

    @PostMapping("/phone/verifications")
    public ResponseEntity<PhoneVerificationSendResponse> sendVerification(
            @Valid @RequestBody PhoneVerificationSendRequest request
    ) {
        PhoneVerificationSendResponse response = authService.sendVerificationCode(request);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }

    @PostMapping("/phone/verifications/confirm")
    public ResponseEntity<PhoneVerificationConfirmResponse> confirmVerification(
            @Valid @RequestBody PhoneVerificationConfirmRequest request
    ) {
        PhoneVerificationConfirmResponse response = authService.confirmVerificationCode(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/phone/login")
    public ResponseEntity<AuthTokenResponse> phoneLogin(
            @Valid @RequestBody PhoneLoginRequest request
    ) {
        AuthTokenResponse response = authService.phoneLogin(request);

        HttpStatus status = response.getUser().getCreatedAt().equals(response.getUser().getUpdatedAt())
                ? HttpStatus.CREATED
                : HttpStatus.OK;

        return ResponseEntity.status(status).body(response);
    }
}
