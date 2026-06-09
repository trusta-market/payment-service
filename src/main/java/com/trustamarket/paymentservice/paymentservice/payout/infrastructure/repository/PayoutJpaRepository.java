package com.trustamarket.paymentservice.paymentservice.payout.infrastructure.repository;

import com.trustamarket.paymentservice.paymentservice.payout.domain.entity.Payout;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PayoutJpaRepository extends JpaRepository<Payout, UUID> {
    boolean existsByPointTxRequestHistoryId(UUID pointTxRequestHistoryId);
}
