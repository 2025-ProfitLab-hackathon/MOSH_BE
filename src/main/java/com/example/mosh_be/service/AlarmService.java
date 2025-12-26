package com.example.mosh_be.service;

import com.example.mosh_be.dto.alarm.AlarmCreateRequest;
import com.example.mosh_be.dto.alarm.AlarmResponse;
import com.example.mosh_be.dto.common.PagedResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AlarmService {

    private final RedisTemplate<String, Object> redisTemplate;

    public PagedResponse<AlarmResponse> getMyAlarms(Long userId, Pageable pageable) {
        // Mock implementation - return empty list
        return PagedResponse.<AlarmResponse>builder()
                .page(0)
                .size(pageable.getPageSize())
                .totalElements(0L)
                .totalPages(0)
                .content(new ArrayList<>())
                .build();
    }

    public AlarmResponse createAlarm(Long userId, AlarmCreateRequest request) {
        String alarmId = "alrm_" + UUID.randomUUID().toString().substring(0, 12);

        // Store in Redis (simplified)
        String key = "alarm:" + userId + ":" + alarmId;
        redisTemplate.opsForValue().set(key, request);

        return AlarmResponse.builder()
                .alarmId(alarmId)
                .festivalId(request.getFestivalId())
                .performanceId(request.getPerformanceId())
                .notifyMinutesBefore(request.getNotifyMinutesBefore())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public void deleteAlarm(Long userId, String alarmId) {
        String key = "alarm:" + userId + ":" + alarmId;
        redisTemplate.delete(key);
    }
}
