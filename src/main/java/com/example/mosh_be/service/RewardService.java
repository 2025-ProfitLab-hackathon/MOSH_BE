package com.example.mosh_be.service;

import com.example.mosh_be.domain.entity.RewardTransaction;
import com.example.mosh_be.domain.entity.User;
import com.example.mosh_be.dto.common.PagedResponse;
import com.example.mosh_be.dto.reward.RewardBalanceResponse;
import com.example.mosh_be.dto.reward.RewardTransactionResponse;
import com.example.mosh_be.exception.ResourceNotFoundException;
import com.example.mosh_be.repository.RewardTransactionRepository;
import com.example.mosh_be.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RewardService {

    private final UserRepository userRepository;
    private final RewardTransactionRepository rewardTransactionRepository;

    public RewardBalanceResponse getRewardBalance(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return new RewardBalanceResponse(user.getReward());
    }

    public PagedResponse<RewardTransactionResponse> getRewardTransactions(Long userId, Pageable pageable) {
        Page<RewardTransaction> transactions = rewardTransactionRepository.findByUserId(userId, pageable);

        List<RewardTransactionResponse> content = transactions.getContent().stream()
                .map(this::toResponse)
                .toList();

        return PagedResponse.<RewardTransactionResponse>builder()
                .page(transactions.getNumber())
                .size(transactions.getSize())
                .totalElements(transactions.getTotalElements())
                .totalPages(transactions.getTotalPages())
                .content(content)
                .build();
    }

    private RewardTransactionResponse toResponse(RewardTransaction transaction) {
        return RewardTransactionResponse.builder()
                .rewardTransactionId(transaction.getRewardTransactionId())
                .type(transaction.getType())
                .amount(transaction.getAmount())
                .createdAt(transaction.getCreatedAt())
                .build();
    }
}
