package com.example.mosh_be.dto.menu;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class MenuListResponse {
    private Long boothId;
    private List<MenuResponse> items;
}
