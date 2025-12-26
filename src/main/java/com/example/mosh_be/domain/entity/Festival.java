package com.example.mosh_be.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "festival")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Festival {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "festival_id")
    private Long festivalId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String place;

    @Column(name = "start_at", nullable = false)
    private LocalDateTime startAt;

    @Column(name = "end_at", nullable = false)
    private LocalDateTime endAt;
}
