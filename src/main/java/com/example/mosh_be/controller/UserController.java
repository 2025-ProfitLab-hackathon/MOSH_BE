package com.example.mosh_be.controller;

import com.example.mosh_be.dto.common.PagedResponse;
import com.example.mosh_be.dto.reward.RewardBalanceResponse;
import com.example.mosh_be.dto.reward.RewardTransactionResponse;
import com.example.mosh_be.dto.user.UserResponse;
import com.example.mosh_be.dto.user.UserUpdateRequest;
import com.example.mosh_be.service.RewardService;
import com.example.mosh_be.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final RewardService rewardService;

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getMyProfile(@AuthenticationPrincipal Long userId) {
        UserResponse response = userService.getMyProfile(userId);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/me")
    public ResponseEntity<UserResponse> updateMyProfile(
            @AuthenticationPrincipal Long userId,
            @RequestBody UserUpdateRequest request
    ) {
        UserResponse response = userService.updateMyProfile(userId, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/me/rewards")
    public ResponseEntity<RewardBalanceResponse> getRewardBalance(@AuthenticationPrincipal Long userId) {
        RewardBalanceResponse response = rewardService.getRewardBalance(userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/me/reward-transactions")
    public ResponseEntity<PagedResponse<RewardTransactionResponse>> getRewardTransactions(
            @AuthenticationPrincipal Long userId,
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        PagedResponse<RewardTransactionResponse> response = rewardService.getRewardTransactions(userId, pageable);
        return ResponseEntity.ok(response);
    }
}
