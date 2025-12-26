package com.example.mosh_be.dto.festival;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class TimetableResponse {
    private Long festivalId;
    private List<PerformanceResponse> items;
}
