package com.trustamarket.paymentservice.paymentservice.domain.entity;

import com.trustamarket.paymentservice.paymentservice.domain.enums.PayoutStatus;
import com.trustamarket.paymentservice.paymentservice.domain.vo.Amount;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@Entity
@Table(name = "p_payouts")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payout extends BaseTimeEntity{

    @Id
    @Column(name = "payout_id", nullable = false, updatable = false)
    private UUID payoutId;

    @Column(name = "wallet_id", nullable = false, updatable = false)
    private UUID walletId;

    @Column(name = "user_id", nullable = false, updatable = false)
    private UUID userId;

    @Column(name = "amount", nullable = false)
    private long amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 30)
    private PayoutStatus status;

    @Version
    @Column(name = "version", nullable = false)
    private Integer version;

    public static Payout create(UUID walletId, UUID userId, Amount amount) {
        Payout payout = new Payout();
        payout.payoutId = UUID.randomUUID();
        payout.walletId = walletId;
        payout.userId = userId;
        payout.amount = amount.value();
        payout.status = PayoutStatus.REQUESTED;
        return payout;
    }

    public void process() {
        if (this.status != PayoutStatus.REQUESTED) {
            throw new IllegalStateException("출금 상태 전이 오류");
        }
        this.status = PayoutStatus.PROCESSING;
    }

    public void complete() {
        if (this.status != PayoutStatus.PROCESSING) {
            throw new IllegalStateException("출금 상태 전이 오류");
        }
        this.status = PayoutStatus.SUCCESS;
    }

    public void fail(String reason) {
        if (this.status != PayoutStatus.REQUESTED && this.status != PayoutStatus.PROCESSING) {
            throw new IllegalStateException("출금 상태 전이 오류");
        }
        this.status = PayoutStatus.FAILED;
    }
}