package com.example.mosh_be.controller;

import com.example.mosh_be.domain.enums.OrderStatus;
import com.example.mosh_be.dto.common.PagedResponse;
import com.example.mosh_be.dto.order.OrderCancelRequest;
import com.example.mosh_be.dto.order.OrderCreateRequest;
import com.example.mosh_be.dto.order.OrderResponse;
import com.example.mosh_be.dto.order.OrderSummaryResponse;
import com.example.mosh_be.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<PagedResponse<OrderSummaryResponse>> getOrders(
            @AuthenticationPrincipal Long userId,
            @RequestParam(required = false) OrderStatus status,
            @RequestParam(required = false) Long boothId,
            @PageableDefault(size = 20, sort = "orderId", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        PagedResponse<OrderSummaryResponse> response = orderService.getMyOrders(userId, status, boothId, pageable);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(
            @AuthenticationPrincipal Long userId,
            @Valid @RequestBody OrderCreateRequest request
    ) {
        OrderResponse response = orderService.createOrder(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrderById(
            @AuthenticationPrincipal Long userId,
            @PathVariable Long orderId
    ) {
        OrderResponse response = orderService.getOrderById(orderId, userId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{orderId}/cancel")
    public ResponseEntity<OrderResponse> cancelOrder(
            @AuthenticationPrincipal Long userId,
            @PathVariable Long orderId,
            @RequestBody OrderCancelRequest request
    ) {
        OrderResponse response = orderService.cancelOrder(orderId, userId, request);
        return ResponseEntity.ok(response);
    }
}
