package com.example.mosh_be.dto.ticket;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class TicketVerifyResponse {
    private String ticketNumber;
    private Boolean isUsed;
    private LocalDateTime verifiedAt;
    private Integer rewardGranted;
    private Integer rewardBalance;
}
