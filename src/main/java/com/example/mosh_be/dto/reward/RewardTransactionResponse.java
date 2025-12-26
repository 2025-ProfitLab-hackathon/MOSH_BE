package com.example.mosh_be.dto.reward;

import com.example.mosh_be.domain.enums.RewardTransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class RewardTransactionResponse {
    private Long rewardTransactionId;
    private RewardTransactionType type;
    private Integer amount;
    private LocalDateTime createdAt;
}
