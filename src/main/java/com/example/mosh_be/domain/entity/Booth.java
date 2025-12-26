package com.example.mosh_be.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "booth", indexes = {
    @Index(name = "idx_booth_festival", columnList = "festival_id")
})
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Booth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booth_id")
    private Long boothId;

    @Column(name = "festival_id", nullable = false)
    private Long festivalId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String place;

    @Column(nullable = false, length = 50)
    private String type;

    @Column(name = "start_at", nullable = false)
    private LocalDateTime startAt;

    @Column(name = "end_at", nullable = false)
    private LocalDateTime endAt;

    @Column(name = "total_review_count")
    @Builder.Default
    private Integer totalReviewCount = 0;

    @Column(name = "avg_review_rating", precision = 4, scale = 2)
    @Builder.Default
    private BigDecimal avgReviewRating = BigDecimal.ZERO;

    public void updateReviewStats(int totalCount, BigDecimal avgRating) {
        this.totalReviewCount = totalCount;
        this.avgReviewRating = avgRating;
    }
}
