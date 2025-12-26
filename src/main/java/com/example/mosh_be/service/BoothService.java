package com.example.mosh_be.service;

import com.example.mosh_be.domain.entity.Booth;
import com.example.mosh_be.dto.booth.BoothResponse;
import com.example.mosh_be.dto.common.PagedResponse;
import com.example.mosh_be.exception.ResourceNotFoundException;
import com.example.mosh_be.repository.BoothRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoothService {

    private final BoothRepository boothRepository;

    public PagedResponse<BoothResponse> searchBooths(Long festivalId, String type, String q, Boolean openNow, Pageable pageable) {
        Page<Booth> booths = boothRepository.searchBooths(festivalId, type, q, pageable);

        List<BoothResponse> content = booths.getContent().stream()
                .map(this::toResponse)
                .toList();

        return PagedResponse.<BoothResponse>builder()
                .page(booths.getNumber())
                .size(booths.getSize())
                .totalElements(booths.getTotalElements())
                .totalPages(booths.getTotalPages())
                .content(content)
                .build();
    }

    public BoothResponse getBoothById(Long boothId) {
        Booth booth = boothRepository.findById(boothId)
                .orElseThrow(() -> new ResourceNotFoundException("Booth not found"));
        return toResponse(booth);
    }

    private BoothResponse toResponse(Booth booth) {
        return BoothResponse.builder()
                .boothId(booth.getBoothId())
                .festivalId(booth.getFestivalId())
                .title(booth.getTitle())
                .place(booth.getPlace())
                .type(booth.getType())
                .startAt(booth.getStartAt())
                .endAt(booth.getEndAt())
                .totalReviewCount(booth.getTotalReviewCount())
                .avgReviewRating(booth.getAvgReviewRating())
                .build();
    }
}
