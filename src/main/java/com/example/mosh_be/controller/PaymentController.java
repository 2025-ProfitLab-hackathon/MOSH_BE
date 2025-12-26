package com.example.mosh_be.controller;

import com.example.mosh_be.dto.payment.PaymentCancelRequest;
import com.example.mosh_be.dto.payment.PaymentConfirmRequest;
import com.example.mosh_be.dto.payment.PaymentCreateRequest;
import com.example.mosh_be.dto.payment.PaymentResponse;
import com.example.mosh_be.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<PaymentResponse> createPayment(
            @AuthenticationPrincipal Long userId,
            @Valid @RequestBody PaymentCreateRequest request
    ) {
        PaymentResponse response = paymentService.createPayment(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/{paymentId}/confirm")
    public ResponseEntity<PaymentResponse> confirmPayment(
            @AuthenticationPrincipal Long userId,
            @PathVariable Long paymentId,
            @Valid @RequestBody PaymentConfirmRequest request
    ) {
        PaymentResponse response = paymentService.confirmPayment(userId, paymentId, request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{paymentId}/cancel")
    public ResponseEntity<PaymentResponse> cancelPayment(
            @AuthenticationPrincipal Long userId,
            @PathVariable Long paymentId,
            @RequestBody PaymentCancelRequest request
    ) {
        PaymentResponse response = paymentService.cancelPayment(userId, paymentId, request);
        return ResponseEntity.ok(response);
    }
}
