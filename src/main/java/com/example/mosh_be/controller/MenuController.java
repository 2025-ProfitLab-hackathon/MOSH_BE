package com.example.mosh_be.controller;

import com.example.mosh_be.dto.menu.MenuListResponse;
import com.example.mosh_be.dto.menu.MenuResponse;
import com.example.mosh_be.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    @GetMapping("/booths/{boothId}/menus")
    public ResponseEntity<MenuListResponse> getMenusByBoothId(@PathVariable Long boothId) {
        MenuListResponse response = menuService.getMenusByBoothId(boothId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/menus/{menuId}")
    public ResponseEntity<MenuResponse> getMenuById(@PathVariable Long menuId) {
        MenuResponse response = menuService.getMenuById(menuId);
        return ResponseEntity.ok(response);
    }
}
