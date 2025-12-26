package com.example.mosh_be.controller;

import com.example.mosh_be.dto.booth.BoothResponse;
import com.example.mosh_be.dto.common.PagedResponse;
import com.example.mosh_be.service.BoothService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/booths")
@RequiredArgsConstructor
public class BoothController {

    private final BoothService boothService;

    @GetMapping
    public ResponseEntity<PagedResponse<BoothResponse>> getBooths(
            @RequestParam(required = false) Long festivalId,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String q,
            @RequestParam(required = false, defaultValue = "false") Boolean openNow,
            @PageableDefault(size = 20, sort = "boothId", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        PagedResponse<BoothResponse> response = boothService.searchBooths(festivalId, type, q, openNow, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{boothId}")
    public ResponseEntity<BoothResponse> getBoothById(@PathVariable Long boothId) {
        BoothResponse response = boothService.getBoothById(boothId);
        return ResponseEntity.ok(response);
    }
}
