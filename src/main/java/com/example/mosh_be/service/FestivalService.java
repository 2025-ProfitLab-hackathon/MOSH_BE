package com.example.mosh_be.service;

import com.example.mosh_be.domain.entity.Festival;
import com.example.mosh_be.dto.common.PagedResponse;
import com.example.mosh_be.dto.festival.FestivalResponse;
import com.example.mosh_be.dto.festival.PerformanceResponse;
import com.example.mosh_be.dto.festival.TimetableResponse;
import com.example.mosh_be.exception.ResourceNotFoundException;
import com.example.mosh_be.repository.FestivalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FestivalService {

    private final FestivalRepository festivalRepository;

    public PagedResponse<FestivalResponse> searchFestivals(String q, LocalDate startFrom, LocalDate startTo, Pageable pageable) {
        LocalDateTime startFromDateTime = startFrom != null ? startFrom.atStartOfDay() : null;
        LocalDateTime startToDateTime = startTo != null ? startTo.atTime(LocalTime.MAX) : null;

        Page<Festival> festivals = festivalRepository.searchFestivals(q, startFromDateTime, startToDateTime, pageable);

        List<FestivalResponse> content = festivals.getContent().stream()
                .map(this::toResponse)
                .toList();

        return PagedResponse.<FestivalResponse>builder()
                .page(festivals.getNumber())
                .size(festivals.getSize())
                .totalElements(festivals.getTotalElements())
                .totalPages(festivals.getTotalPages())
                .content(content)
                .build();
    }

    public FestivalResponse getFestivalById(Long festivalId) {
        Festival festival = festivalRepository.findById(festivalId)
                .orElseThrow(() -> new ResourceNotFoundException("Festival not found"));
        return toResponse(festival);
    }

    public TimetableResponse getTimetable(Long festivalId, LocalDate date) {
        // Mock timetable for MVP
        List<PerformanceResponse> performances = new ArrayList<>();

        // Add sample performances
        performances.add(PerformanceResponse.builder()
                .performanceId("perf_001")
                .festivalId(festivalId)
                .title("헤드라이너 A")
                .stage("Main Stage")
                .startAt(LocalDateTime.now().plusHours(2))
                .endAt(LocalDateTime.now().plusHours(4))
                .build());

        return TimetableResponse.builder()
                .festivalId(festivalId)
                .items(performances)
                .build();
    }

    private FestivalResponse toResponse(Festival festival) {
        return FestivalResponse.builder()
                .festivalId(festival.getFestivalId())
                .title(festival.getTitle())
                .place(festival.getPlace())
                .startAt(festival.getStartAt())
                .endAt(festival.getEndAt())
                .build();
    }
}
