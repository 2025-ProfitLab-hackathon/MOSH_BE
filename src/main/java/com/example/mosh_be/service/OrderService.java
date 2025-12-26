package com.example.mosh_be.service;

import com.example.mosh_be.domain.entity.Menu;
import com.example.mosh_be.domain.entity.Order;
import com.example.mosh_be.domain.entity.OrderItem;
import com.example.mosh_be.domain.enums.OrderStatus;
import com.example.mosh_be.domain.enums.ReservationStatus;
import com.example.mosh_be.dto.common.PagedResponse;
import com.example.mosh_be.dto.order.*;
import com.example.mosh_be.exception.ResourceNotFoundException;
import com.example.mosh_be.repository.MenuRepository;
import com.example.mosh_be.repository.OrderItemRepository;
import com.example.mosh_be.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final MenuRepository menuRepository;

    @Transactional
    public OrderResponse createOrder(Long userId, OrderCreateRequest request) {
        // Calculate total price
        int totalPrice = 0;
        for (OrderItemCreateRequest item : request.getItems()) {
            Menu menu = menuRepository.findById(item.getMenuId())
                    .orElseThrow(() -> new ResourceNotFoundException("Menu not found"));
            totalPrice += menu.getPrice() * item.getQuantity();
        }

        // Generate order number
        String orderNumber = "ORD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        // Create order
        Order order = Order.builder()
                .userId(userId)
                .boothId(request.getBoothId())
                .orderNumber(orderNumber)
                .type(request.getType())
                .isPickup(false)
                .status(OrderStatus.READY)
                .totalPrice(totalPrice)
                .build();
        order = orderRepository.save(order);

        // Create order items
        for (OrderItemCreateRequest itemReq : request.getItems()) {
            Menu menu = menuRepository.findById(itemReq.getMenuId())
                    .orElseThrow(() -> new ResourceNotFoundException("Menu not found"));

            OrderItem orderItem = OrderItem.builder()
                    .orderId(order.getOrderId())
                    .menuId(menu.getMenuId())
                    .quantity(itemReq.getQuantity())
                    .unitPrice(menu.getPrice())
                    .menuName(menu.getName())
                    .lineTotalPrice(menu.getPrice() * itemReq.getQuantity())
                    .build();
            orderItemRepository.save(orderItem);
        }

        return getOrderById(order.getOrderId(), userId);
    }

    public OrderResponse getOrderById(Long orderId, Long userId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        List<OrderItem> orderItems = orderItemRepository.findByOrderId(orderId);

        List<OrderItemResponse> items = orderItems.stream()
                .map(this::toOrderItemResponse)
                .toList();

        return OrderResponse.builder()
                .orderId(order.getOrderId())
                .userId(order.getUserId())
                .boothId(order.getBoothId())
                .orderNumber(order.getOrderNumber())
                .type(order.getType())
                .pickupMethod(null)
                .reservationStatus(ReservationStatus.QUEUED)
                .pickupSlotId(null)
                .pickupAt(null)
                .pickupWindow(null)
                .queueNumber(null)
                .estimatedWaitMinutes(20)
                .isPickup(order.getIsPickup())
                .status(order.getStatus())
                .totalPrice(order.getTotalPrice())
                .items(items)
                .build();
    }

    public PagedResponse<OrderSummaryResponse> getMyOrders(Long userId, OrderStatus status, Long boothId, Pageable pageable) {
        Page<Order> orders = orderRepository.searchOrders(userId, status, boothId, pageable);

        List<OrderSummaryResponse> content = orders.getContent().stream()
                .map(this::toOrderSummaryResponse)
                .toList();

        return PagedResponse.<OrderSummaryResponse>builder()
                .page(orders.getNumber())
                .size(orders.getSize())
                .totalElements(orders.getTotalElements())
                .totalPages(orders.getTotalPages())
                .content(content)
                .build();
    }

    @Transactional
    public OrderResponse cancelOrder(Long orderId, Long userId, OrderCancelRequest request) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        order.updateStatus(OrderStatus.CANCELED);
        return getOrderById(orderId, userId);
    }

    private OrderItemResponse toOrderItemResponse(OrderItem item) {
        return OrderItemResponse.builder()
                .orderItemId(item.getOrderItemId())
                .menuId(item.getMenuId())
                .quantity(item.getQuantity())
                .unitPrice(item.getUnitPrice())
                .menuName(item.getMenuName())
                .lineTotalPrice(item.getLineTotalPrice())
                .build();
    }

    private OrderSummaryResponse toOrderSummaryResponse(Order order) {
        return OrderSummaryResponse.builder()
                .orderId(order.getOrderId())
                .boothId(order.getBoothId())
                .orderNumber(order.getOrderNumber())
                .type(order.getType())
                .pickupMethod(null)
                .reservationStatus(ReservationStatus.QUEUED)
                .pickupAt(null)
                .estimatedWaitMinutes(20)
                .status(order.getStatus())
                .totalPrice(order.getTotalPrice())
                .createdAt(null)
                .build();
    }
}
