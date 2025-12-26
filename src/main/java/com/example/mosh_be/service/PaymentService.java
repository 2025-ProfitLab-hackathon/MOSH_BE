package com.example.mosh_be.service;

import com.example.mosh_be.domain.entity.Order;
import com.example.mosh_be.domain.entity.Payment;
import com.example.mosh_be.domain.entity.RewardTransaction;
import com.example.mosh_be.domain.entity.User;
import com.example.mosh_be.domain.enums.PaymentMethod;
import com.example.mosh_be.domain.enums.PaymentStatus;
import com.example.mosh_be.domain.enums.RewardTransactionType;
import com.example.mosh_be.dto.payment.*;
import com.example.mosh_be.exception.BadRequestException;
import com.example.mosh_be.exception.ResourceNotFoundException;
import com.example.mosh_be.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final RewardTransactionRepository rewardTransactionRepository;

    @Transactional
    public PaymentResponse createPayment(Long userId, PaymentCreateRequest request) {
        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        // Check if payment already exists
        paymentRepository.findByOrderId(order.getOrderId())
                .ifPresent(p -> {
                    throw new BadRequestException("Payment already exists for this order");
                });

        Payment payment = Payment.builder()
                .orderId(order.getOrderId())
                .status(PaymentStatus.READY)
                .build();
        payment = paymentRepository.save(payment);

        return toResponse(payment);
    }

    @Transactional
    public PaymentResponse confirmPayment(Long userId, Long paymentId, PaymentConfirmRequest request) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found"));

        Order order = orderRepository.findById(payment.getOrderId())
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        // If CASH payment, deduct reward
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (request.getPgTransactionId() == null) {
            // CASH payment
            user.useReward(order.getTotalPrice());

            // Create reward transaction
            RewardTransaction transaction = RewardTransaction.builder()
                    .userId(userId)
                    .type(RewardTransactionType.USE)
                    .amount(-order.getTotalPrice())
                    .build();
            rewardTransactionRepository.save(transaction);
        }

        payment.updateStatus(PaymentStatus.PAID);
        return toResponse(payment);
    }

    @Transactional
    public PaymentResponse cancelPayment(Long userId, Long paymentId, PaymentCancelRequest request) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found"));

        payment.updateStatus(PaymentStatus.CANCELED);
        return toResponse(payment);
    }

    private PaymentResponse toResponse(Payment payment) {
        return PaymentResponse.builder()
                .paymentId(payment.getPaymentId())
                .orderId(payment.getOrderId())
                .status(payment.getStatus())
                .createdAt(payment.getCreatedAt())
                .build();
    }
}
