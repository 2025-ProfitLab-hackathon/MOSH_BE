package com.example.mosh_be.service;

import com.example.mosh_be.domain.entity.Review;
import com.example.mosh_be.dto.common.PagedResponse;
import com.example.mosh_be.dto.review.ReviewCreateRequest;
import com.example.mosh_be.dto.review.ReviewResponse;
import com.example.mosh_be.exception.ResourceNotFoundException;
import com.example.mosh_be.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public PagedResponse<ReviewResponse> getReviews(Long menuId, Integer ratingGte, Pageable pageable) {
        Page<Review> reviews = reviewRepository.searchReviews(menuId, ratingGte, pageable);

        List<ReviewResponse> content = reviews.getContent().stream()
                .map(this::toResponse)
                .toList();

        return PagedResponse.<ReviewResponse>builder()
                .page(reviews.getNumber())
                .size(reviews.getSize())
                .totalElements(reviews.getTotalElements())
                .totalPages(reviews.getTotalPages())
                .content(content)
                .build();
    }

    @Transactional
    public ReviewResponse createReview(Long userId, Long menuId, ReviewCreateRequest request) {
        Review review = Review.builder()
                .menuId(menuId)
                .rating(request.getRating())
                .content(request.getContent())
                .imageUrl(request.getImageUrl())
                .build();

        review = reviewRepository.save(review);
        return toResponse(review);
    }

    public ReviewResponse getReviewById(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found"));
        return toResponse(review);
    }

    private ReviewResponse toResponse(Review review) {
        return ReviewResponse.builder()
                .reviewId(review.getReviewId())
                .menuId(review.getMenuId())
                .rating(review.getRating())
                .content(review.getContent())
                .imageUrl(review.getImageUrl())
                .createdAt(review.getCreatedAt())
                .build();
    }
}
