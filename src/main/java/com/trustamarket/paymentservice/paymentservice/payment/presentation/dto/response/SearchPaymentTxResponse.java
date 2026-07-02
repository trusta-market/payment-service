package com.trustamarket.paymentservice.paymentservice.payment.presentation.dto.response;

import com.trustamarket.paymentservice.paymentservice.payment.application.dto.result.SearchPaymentTxResult;
import com.trustamarket.paymentservice.paymentservice.payment.domain.enums.PaymentTxType;

import java.time.Instant;
import java.util.UUID;

public record SearchPaymentTxResponse(
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
    public static SearchPaymentTxResponse from(SearchPaymentTxResult result) {
        return new SearchPaymentTxResponse(
                result.paymentTxId(),
                result.paymentId(),
                result.userId(),
                result.txType(),
                result.amount(),
                result.paymentKey(),
                result.pgResponseCode(),
                result.pgResponseMessage(),
                result.createdAt()
        );
    }
}
