package com.example.mosh_be.dto.menu;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class MenuResponse {
    private Long menuId;
    private Long boothId;
    private String name;
    private Integer price;
    private String imageUrl;
}
