package com.trustamarket.paymentservice.paymentservice.payout.domain.entity;

import com.trustamarket.common.domain.BaseTimeEntity;
import com.trustamarket.paymentservice.paymentservice.payout.domain.enums.PayoutStatus;
import com.trustamarket.paymentservice.paymentservice.payment.domain.exception.PaymentException;
import com.trustamarket.paymentservice.paymentservice.payout.domain.exception.PayoutErrorCode;
import com.trustamarket.paymentservice.paymentservice.payment.domain.vo.Amount;
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
public class Payout extends BaseTimeEntity {

    @Id
    @Column(name = "payout_id", nullable = false, updatable = false)
    private UUID payoutId;

    @Column(name = "point_tx_history_id", nullable = false, updatable = false)
    private UUID pointTxHistoryId;

    @Column(name = "user_id", nullable = false, updatable = false)
    private UUID userId;

    @Column(name = "bank_code", length = 30)
    private String bankCode;

    @Column(name = "account_number", length = 30)
    private String accountNumber;

    @Column(name = "amount", nullable = false)
    private long amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 30)
    private PayoutStatus status;

    @Version
    @Column(name = "version", nullable = false)
    private Integer version;

    public static Payout create(UUID pointTxHistoryId, UUID userId, Amount amount) {
        Payout payout = new Payout();
        payout.payoutId = UUID.randomUUID();
        payout.pointTxHistoryId = pointTxHistoryId;
        payout.userId = userId;
        payout.amount = amount.value();
        payout.status = PayoutStatus.REQUESTED;
        return payout;
    }

    public void complete() {
        if (this.status != PayoutStatus.REQUESTED) {
            throw new PaymentException(PayoutErrorCode.INVALID_PAYOUT_STATUS);
        }
        this.status = PayoutStatus.SUCCESS;
    }

    public void fail(String reason) {
        if (this.status != PayoutStatus.REQUESTED) {
            throw new PaymentException(PayoutErrorCode.INVALID_PAYOUT_STATUS);
        }
        this.status = PayoutStatus.FAILED;
    }
}