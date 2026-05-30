package com.trustamarket.paymentservice.paymentservice.payment.infrastructure.wallet.retry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface WalletChargeRetryJpaRepository extends JpaRepository<WalletChargeRetry, UUID> {

    @Query("SELECT r FROM WalletChargeRetry r WHERE r.status = :status AND r.lastAttemptedAt < :threshold")
    List<WalletChargeRetry> findRetriable(@Param("status") RetryStatus status, @Param("threshold") LocalDateTime threshold);
}
