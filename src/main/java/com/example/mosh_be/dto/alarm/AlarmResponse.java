package com.example.mosh_be.dto.alarm;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class AlarmResponse {
    private String alarmId;
    private Long festivalId;
    private String performanceId;
    private Integer notifyMinutesBefore;
    private LocalDateTime createdAt;
}
