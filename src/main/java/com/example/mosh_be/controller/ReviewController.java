package com.example.mosh_be.controller;

import com.example.mosh_be.dto.common.PagedResponse;
import com.example.mosh_be.dto.review.ReviewCreateRequest;
import com.example.mosh_be.dto.review.ReviewResponse;
import com.example.mosh_be.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/menus/{menuId}/reviews")
    public ResponseEntity<PagedResponse<ReviewResponse>> getReviews(
            @PathVariable Long menuId,
            @RequestParam(required = false) Integer ratingGte,
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        PagedResponse<ReviewResponse> response = reviewService.getReviews(menuId, ratingGte, pageable);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/menus/{menuId}/reviews")
    public ResponseEntity<ReviewResponse> createReview(
            @AuthenticationPrincipal Long userId,
            @PathVariable Long menuId,
            @Valid @RequestBody ReviewCreateRequest request
    ) {
        ReviewResponse response = reviewService.createReview(userId, menuId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/reviews/{reviewId}")
    public ResponseEntity<ReviewResponse> getReviewById(@PathVariable Long reviewId) {
        ReviewResponse response = reviewService.getReviewById(reviewId);
        return ResponseEntity.ok(response);
    }
}
