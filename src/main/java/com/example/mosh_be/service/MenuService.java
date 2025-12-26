package com.example.mosh_be.service;

import com.example.mosh_be.domain.entity.Menu;
import com.example.mosh_be.dto.menu.MenuListResponse;
import com.example.mosh_be.dto.menu.MenuResponse;
import com.example.mosh_be.exception.ResourceNotFoundException;
import com.example.mosh_be.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;

    public MenuListResponse getMenusByBoothId(Long boothId) {
        List<Menu> menus = menuRepository.findByBoothId(boothId);

        List<MenuResponse> items = menus.stream()
                .map(this::toResponse)
                .toList();

        return MenuListResponse.builder()
                .boothId(boothId)
                .items(items)
                .build();
    }

    public MenuResponse getMenuById(Long menuId) {
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new ResourceNotFoundException("Menu not found"));
        return toResponse(menu);
    }

    private MenuResponse toResponse(Menu menu) {
        return MenuResponse.builder()
                .menuId(menu.getMenuId())
                .boothId(menu.getBoothId())
                .name(menu.getName())
                .price(menu.getPrice())
                .imageUrl(menu.getImageUrl())
                .build();
    }
}
