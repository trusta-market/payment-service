package com.trustamarket.paymentservice.paymentservice.payment.application.dto.result;

import com.trustamarket.paymentservice.paymentservice.payment.domain.entity.PaymentTx;
import com.trustamarket.paymentservice.paymentservice.payment.domain.enums.PaymentTxType;

import java.time.Instant;
import java.util.UUID;

public record SearchPaymentTxResult(
        UUID paymentTxId,
        UUID paymentId,
        UUID userId,
        PaymentTxType txType,
        long amount,
        String paymentKey,
        String pgResponseCode,
        String pgResponseMessage,
        Instant createdAt
) {
    public static SearchPaymentTxResult from(PaymentTx tx) {
        return new SearchPaymentTxResult(
                tx.getPaymentTxId(),
                tx.getPayment().getPaymentId(),
                tx.getUserId(),
                tx.getTxType(),
                tx.getAmount(),
                tx.getPaymentKey(),
                tx.getPgResponseCode(),
                tx.getPgResponseMessage(),
                tx.getCreatedAt()
        );
    }
}
