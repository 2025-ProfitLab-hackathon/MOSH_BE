package com.example.mosh_be.domain.entity;

import com.example.mosh_be.domain.enums.OrderStatus;
import com.example.mosh_be.domain.enums.OrderType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "orders",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_order_number", columnNames = "order_number")
    },
    indexes = {
        @Index(name = "idx_order_user", columnList = "user_id"),
        @Index(name = "idx_order_booth", columnList = "booth_id")
    }
)
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "booth_id", nullable = false)
    private Long boothId;

    @Column(name = "order_number", nullable = false)
    private String orderNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private OrderType type;

    @Column(name = "is_pickup", nullable = false)
    private Boolean isPickup;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private OrderStatus status;

    @Column(name = "total_price", nullable = false)
    private Integer totalPrice;

    public void updateStatus(OrderStatus status) {
        this.status = status;
    }

    public void completePickup() {
        this.isPickup = true;
    }
}
