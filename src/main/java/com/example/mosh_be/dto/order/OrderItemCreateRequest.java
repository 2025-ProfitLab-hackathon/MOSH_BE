package com.example.mosh_be.dto.order;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemCreateRequest {
    @NotNull
    private Long menuId;

    @NotNull
    @Min(1)
    private Integer quantity;
}
