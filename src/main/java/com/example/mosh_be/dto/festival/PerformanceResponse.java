package com.example.mosh_be.dto.festival;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class PerformanceResponse {
    private String performanceId;
    private Long festivalId;
    private String title;
    private String stage;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
}
