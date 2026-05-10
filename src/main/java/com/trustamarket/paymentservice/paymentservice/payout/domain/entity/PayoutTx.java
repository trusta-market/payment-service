package com.trustamarket.paymentservice.paymentservice.payout.domain.entity;

import com.trustamarket.common.domain.BaseCreatedEntity;
import com.trustamarket.paymentservice.paymentservice.payout.domain.enums.PayoutTxType;
import com.trustamarket.paymentservice.paymentservice.payout.domain.vo.Amount;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Entity
@Table(name = "p_payout_transactions")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PayoutTx extends BaseCreatedEntity {

    @Id
    @Column(name = "payout_tx_id", nullable = false, updatable = false)
    private UUID payoutTxId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "payout_id", nullable = false)
    private Payout payout;

    @Enumerated(EnumType.STRING)
    @Column(name = "tx_type", nullable = false, length = 30, updatable = false)
    private PayoutTxType txType;

    @Column(name = "amount", nullable = false)
    private long amount;

    @Column(name = "failure_reason", length = 255)
    private String failureReason;

    @OneToMany(mappedBy = "payout", cascade = CascadeType.PERSIST)
    private List<PayoutTx> transactions = new ArrayList<>();

    public static PayoutTx createRequest(Amount amount) {
        PayoutTx tx = new PayoutTx();
        tx.payoutTxId = UUID.randomUUID();
        tx.txType = PayoutTxType.REQUESTED;
        tx.amount = amount.value();
        return tx;
    }

    public static PayoutTx createSuccess(Amount amount) {
        PayoutTx tx = new PayoutTx();
        tx.payoutTxId = UUID.randomUUID();
        tx.txType = PayoutTxType.SUCCESS;
        tx.amount = amount.value();
        return tx;
    }

    public static PayoutTx createFail(Amount amount, String failureReason) {
        PayoutTx tx = new PayoutTx();
        tx.payoutTxId = UUID.randomUUID();
        tx.txType = PayoutTxType.FAILED;
        tx.amount = amount.value();
        tx.failureReason = failureReason;
        return tx;
    }

    void assignPayout(Payout payout) {
        this.payout = payout;
    }
}
