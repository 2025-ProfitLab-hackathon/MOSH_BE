package com.example.mosh_be.domain.entity;

import com.example.mosh_be.domain.enums.RewardTransactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "reward_transaction", indexes = {
    @Index(name = "idx_reward_user", columnList = "user_id")
})
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RewardTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reward_transaction_id")
    private Long rewardTransactionId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private RewardTransactionType type;

    @Column(nullable = false)
    private Integer amount;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
