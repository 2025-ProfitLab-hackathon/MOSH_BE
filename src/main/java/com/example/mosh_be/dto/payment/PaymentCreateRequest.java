package com.example.mosh_be.dto.payment;

import com.example.mosh_be.domain.enums.PaymentMethod;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentCreateRequest {
    @NotNull
    private Long orderId;

    @NotNull
    private PaymentMethod method;

    private String idempotencyKey;
}
