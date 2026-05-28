package com.trustamarket.paymentservice.paymentservice.payout.infrastructure.withdraw.retry;

import com.trustamarket.common.domain.BaseTimeEntity;
import com.trustamarket.paymentservice.paymentservice.payout.application.port.out.wallet.PayoutCompletedResult;
import com.trustamarket.paymentservice.paymentservice.payout.domain.enums.PayoutStatus;
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
@Table(name = "p_wallet_withdraw_retry")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WalletWithdrawRetry extends BaseTimeEntity {

    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "payout_id", nullable = false)
    private UUID payoutId;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "point_tx_request_history_id", nullable = false)
    private UUID pointTxRequestHistoryId;

    @Enumerated(EnumType.STRING)
    @Column(name = "payout_status", nullable = false, length = 20)
    private PayoutStatus payoutStatus;

    @Column(name = "amount", nullable = false)
    private long amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private RetryStatus status;

    @Column(name = "retry_count", nullable = false)
    private int retryCount;

    @Column(name = "last_attempted_at")
    private LocalDateTime lastAttemptedAt;

    public static WalletWithdrawRetry create(PayoutCompletedResult result) {
        WalletWithdrawRetry retry = new WalletWithdrawRetry();
        retry.id = UUID.randomUUID();
        retry.payoutId = result.payoutId();
        retry.userId = result.userId();
        retry.pointTxRequestHistoryId = result.pointTxRequestHistoryId();
        retry.payoutStatus = result.payoutStatus();
        retry.amount = result.payoutAmount();
        retry.status = RetryStatus.PENDING;
        retry.retryCount = 0;
        return retry;
    }

    public void markSuccess() {
        this.status = RetryStatus.SUCCESS;
        this.lastAttemptedAt = LocalDateTime.now();
    }

    public void recordFailure() {
        this.retryCount++;
        this.lastAttemptedAt = LocalDateTime.now();
        if (this.retryCount >= 5) {
            this.status = RetryStatus.FAILED;
        }
    }

    public boolean needsAdminAlert() {
        return this.retryCount == 3;
    }
}
