package com.example.mosh_be.dto.order;

import com.example.mosh_be.domain.enums.OrderType;
import com.example.mosh_be.domain.enums.PickupMethod;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreateRequest {
    @NotNull
    private Long boothId;

    @NotNull
    private OrderType type;

    @NotNull
    private PickupMethod pickupMethod;

    private String pickupSlotId;
    private LocalDateTime requestedPickupAt;

    @NotEmpty
    private List<OrderItemCreateRequest> items;
}
