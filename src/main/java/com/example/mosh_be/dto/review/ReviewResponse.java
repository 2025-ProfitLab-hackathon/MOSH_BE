package com.example.mosh_be.dto.review;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class ReviewResponse {
    private Long reviewId;
    private Long menuId;
    private Integer rating;
    private String content;
    private String imageUrl;
    private LocalDateTime createdAt;
}
