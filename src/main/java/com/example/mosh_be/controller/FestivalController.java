package com.example.mosh_be.controller;

import com.example.mosh_be.dto.common.PagedResponse;
import com.example.mosh_be.dto.festival.FestivalResponse;
import com.example.mosh_be.dto.festival.TimetableResponse;
import com.example.mosh_be.service.FestivalService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/festivals")
@RequiredArgsConstructor
public class FestivalController {

    private final FestivalService festivalService;

    @GetMapping
    public ResponseEntity<PagedResponse<FestivalResponse>> getFestivals(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startFrom,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startTo,
            @PageableDefault(size = 20) Pageable pageable
    ) {
        PagedResponse<FestivalResponse> response = festivalService.searchFestivals(q, startFrom, startTo, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{festivalId}")
    public ResponseEntity<FestivalResponse> getFestivalById(@PathVariable Long festivalId) {
        FestivalResponse response = festivalService.getFestivalById(festivalId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{festivalId}/timetable")
    public ResponseEntity<TimetableResponse> getTimetable(
            @PathVariable Long festivalId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        TimetableResponse response = festivalService.getTimetable(festivalId, date);
        return ResponseEntity.ok(response);
    }
}
