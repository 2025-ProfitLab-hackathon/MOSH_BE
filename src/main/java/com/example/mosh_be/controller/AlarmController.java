package com.example.mosh_be.controller;

import com.example.mosh_be.dto.alarm.AlarmCreateRequest;
import com.example.mosh_be.dto.alarm.AlarmResponse;
import com.example.mosh_be.dto.common.PagedResponse;
import com.example.mosh_be.service.AlarmService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/alarms")
@RequiredArgsConstructor
public class AlarmController {

    private final AlarmService alarmService;

    @GetMapping
    public ResponseEntity<PagedResponse<AlarmResponse>> getAlarms(
            @AuthenticationPrincipal Long userId,
            @PageableDefault(size = 20) Pageable pageable
    ) {
        PagedResponse<AlarmResponse> response = alarmService.getMyAlarms(userId, pageable);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<AlarmResponse> createAlarm(
            @AuthenticationPrincipal Long userId,
            @Valid @RequestBody AlarmCreateRequest request
    ) {
        AlarmResponse response = alarmService.createAlarm(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{alarmId}")
    public ResponseEntity<Void> deleteAlarm(
            @AuthenticationPrincipal Long userId,
            @PathVariable String alarmId
    ) {
        alarmService.deleteAlarm(userId, alarmId);
        return ResponseEntity.noContent().build();
    }
}
