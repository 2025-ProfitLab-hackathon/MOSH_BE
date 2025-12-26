package com.example.mosh_be.dto.order;

import com.example.mosh_be.domain.enums.OrderStatus;
import com.example.mosh_be.domain.enums.OrderType;
import com.example.mosh_be.domain.enums.PickupMethod;
import com.example.mosh_be.domain.enums.ReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class OrderResponse {
    private Long orderId;
    private Long userId;
    private Long boothId;
    private String orderNumber;
    private OrderType type;
    private PickupMethod pickupMethod;
    private ReservationStatus reservationStatus;
    private String pickupSlotId;
    private String pickupAt;
    private PickupWindow pickupWindow;
    private Integer queueNumber;
    private Integer estimatedWaitMinutes;
    private Boolean isPickup;
    private OrderStatus status;
    private Integer totalPrice;
    private List<OrderItemResponse> items;

    @Getter
    @AllArgsConstructor
    @Builder
    public static class PickupWindow {
        private String startAt;
        private String endAt;
    }
}
