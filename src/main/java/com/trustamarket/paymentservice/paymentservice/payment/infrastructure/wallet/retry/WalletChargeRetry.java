package com.trustamarket.paymentservice.paymentservice.payment.infrastructure.wallet.retry;

import com.trustamarket.common.domain.BaseTimeEntity;
import com.trustamarket.paymentservice.paymentservice.payment.application.port.PaymentResponseResult;
import com.trustamarket.paymentservice.paymentservice.payment.domain.enums.PaymentStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Entity
@Table(name = "p_wallet_charge_retry")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WalletChargeRetry extends BaseTimeEntity {

    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "payment_id", unique = true, nullable = false)
    private UUID paymentId;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "point_tx_request_history_id", nullable = false)
    private UUID pointTxRequestHistoryId;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", nullable = false, length = 20)
    private PaymentStatus paymentStatus;

    @Column(name = "amount", nullable = false)
    private long amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private RetryStatus status;

    @Column(name = "retry_count", nullable = false)
    private int retryCount;

    @Column(name = "last_attempted_at")
    private LocalDateTime lastAttemptedAt;

    public static WalletChargeRetry create(PaymentResponseResult result) {
        WalletChargeRetry retry = new WalletChargeRetry();
        retry.id = UUID.randomUUID();
        retry.paymentId = result.paymentId();
        retry.userId = result.userId();
        retry.pointTxRequestHistoryId = result.pointTxRequestHistoryId();
        retry.paymentStatus = result.paymentStatus();
        retry.amount = result.amount();
        retry.status = RetryStatus.PENDING;
        retry.retryCount = 0;
        retry.lastAttemptedAt = LocalDateTime.now();
        return retry;
    }

    public void markSuccess() {
        this.status = RetryStatus.SUCCESS;
        this.lastAttemptedAt = LocalDateTime.now();
    }

    public void recordFailure() {
        this.retryCount++;
        this.lastAttemptedAt = LocalDateTime.now();
        if (this.retryCount >= 2) {
            this.status = RetryStatus.FAILED;
        }
    }

}
