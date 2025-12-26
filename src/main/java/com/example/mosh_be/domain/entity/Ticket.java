package com.example.mosh_be.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "ticket",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_ticket_number", columnNames = "ticket_number")
    },
    indexes = {
        @Index(name = "idx_ticket_user", columnList = "user_id")
    }
)
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_id")
    private Long ticketId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "ticket_number", nullable = false)
    private String ticketNumber;

    @Column(name = "is_used")
    @Builder.Default
    private Boolean isUsed = false;

    @Column(name = "verified_at")
    private LocalDateTime verifiedAt;

    public void verify() {
        this.isUsed = true;
        this.verifiedAt = LocalDateTime.now();
    }
}
