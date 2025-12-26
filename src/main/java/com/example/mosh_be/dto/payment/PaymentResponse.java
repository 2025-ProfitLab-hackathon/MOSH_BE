package com.example.mosh_be.dto.payment;

import com.example.mosh_be.domain.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class PaymentResponse {
    private Long paymentId;
    private Long orderId;
    private PaymentStatus status;
    private LocalDateTime createdAt;
}
