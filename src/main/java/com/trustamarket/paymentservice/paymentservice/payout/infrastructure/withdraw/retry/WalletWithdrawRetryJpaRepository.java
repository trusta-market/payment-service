package com.trustamarket.paymentservice.paymentservice.payout.infrastructure.withdraw.retry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface WalletWithdrawRetryJpaRepository extends JpaRepository<WalletWithdrawRetry, UUID> {

    @Query("SELECT r FROM WalletWithdrawRetry r WHERE r.status = :status AND (r.lastAttemptedAt IS NULL OR r.lastAttemptedAt < :threshold)")
    List<WalletWithdrawRetry> findRetriable(@Param("status") RetryStatus status, @Param("threshold") LocalDateTime threshold);
}
