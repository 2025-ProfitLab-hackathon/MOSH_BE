package com.example.mosh_be.dto.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class OrderItemResponse {
    private Long orderItemId;
    private Long menuId;
    private Integer quantity;
    private Integer unitPrice;
    private String menuName;
    private Integer lineTotalPrice;
}
