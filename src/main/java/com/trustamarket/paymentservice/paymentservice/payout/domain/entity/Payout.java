package com.trustamarket.paymentservice.paymentservice.payout.domain.entity;

import com.trustamarket.common.domain.BaseTimeEntity;
import com.trustamarket.paymentservice.paymentservice.payout.domain.enums.PayoutStatus;
import com.trustamarket.paymentservice.paymentservice.payout.domain.exception.PayoutErrorCode;
import com.trustamarket.paymentservice.paymentservice.payout.domain.exception.PayoutException;
import com.trustamarket.paymentservice.paymentservice.payout.domain.vo.Amount;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Entity
@Table(name = "p_payouts")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payout extends BaseTimeEntity {

    @Id
    @Column(name = "payout_id", nullable = false, updatable = false)
    private UUID payoutId;

    @Column(name = "point_tx_request_history_id", nullable = false, unique = true)
    private UUID pointTxRequestHistoryId;

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

    @OneToMany(mappedBy = "payout", cascade = CascadeType.PERSIST)
    private List<PayoutTx> transactions = new ArrayList<>();

    public static Payout create(UUID userId, UUID pointTxRequestHistoryId, Amount amount) {
        Payout payout = new Payout();
        payout.payoutId = UUID.randomUUID();
        payout.pointTxRequestHistoryId = pointTxRequestHistoryId;
        payout.userId = userId;
        payout.amount = amount.value();
        payout.status = PayoutStatus.REQUESTED;
        return payout;
    }

    public void complete() {
        if (this.status != PayoutStatus.REQUESTED) {
            throw new PayoutException(PayoutErrorCode.INVALID_PAYOUT_STATUS);
        }
        this.status = PayoutStatus.SUCCESS;

        this.addTransaction(PayoutTx.createSuccess(Amount.of(this.amount)));
    }

    public void fail(String reason) {
        if (this.status != PayoutStatus.REQUESTED) {
            throw new PayoutException(PayoutErrorCode.INVALID_PAYOUT_STATUS);
        }
        this.status = PayoutStatus.FAILED;

        this.addTransaction(PayoutTx.createFail(Amount.of(this.amount), reason));
    }

    private void addTransaction(PayoutTx transaction) {
        this.transactions.add(transaction);
        transaction.assignPayout(this);
    }
}