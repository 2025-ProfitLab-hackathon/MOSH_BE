package com.example.mosh_be.dto.booth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class BoothResponse {
    private Long boothId;
    private Long festivalId;
    private String title;
    private String place;
    private String type;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private Integer totalReviewCount;
    private BigDecimal avgReviewRating;
}
