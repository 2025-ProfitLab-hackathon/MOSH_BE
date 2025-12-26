package com.example.mosh_be.dto.order;

import com.example.mosh_be.domain.enums.OrderStatus;
import com.example.mosh_be.domain.enums.OrderType;
import com.example.mosh_be.domain.enums.PickupMethod;
import com.example.mosh_be.domain.enums.ReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class OrderSummaryResponse {
    private Long orderId;
    private Long boothId;
    private String orderNumber;
    private OrderType type;
    private PickupMethod pickupMethod;
    private ReservationStatus reservationStatus;
    private String pickupAt;
    private Integer estimatedWaitMinutes;
    private OrderStatus status;
    private Integer totalPrice;
    private LocalDateTime createdAt;
}
