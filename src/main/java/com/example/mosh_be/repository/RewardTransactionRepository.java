package com.example.mosh_be.repository;

import com.example.mosh_be.domain.entity.RewardTransaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RewardTransactionRepository extends JpaRepository<RewardTransaction, Long> {
    Page<RewardTransaction> findByUserId(Long userId, Pageable pageable);
}
