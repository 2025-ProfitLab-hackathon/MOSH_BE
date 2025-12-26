package com.example.mosh_be.repository;

import com.example.mosh_be.domain.entity.Order;
import com.example.mosh_be.domain.enums.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findByOrderNumber(String orderNumber);

    Page<Order> findByUserId(Long userId, Pageable pageable);

    @Query("SELECT o FROM Order o WHERE " +
           "o.userId = :userId AND " +
           "(:status IS NULL OR o.status = :status) AND " +
           "(:boothId IS NULL OR o.boothId = :boothId)")
    Page<Order> searchOrders(@Param("userId") Long userId,
                             @Param("status") OrderStatus status,
                             @Param("boothId") Long boothId,
                             Pageable pageable);
}
