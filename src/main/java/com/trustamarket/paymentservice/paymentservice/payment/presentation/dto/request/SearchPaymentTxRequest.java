package com.trustamarket.paymentservice.paymentservice.payment.presentation.dto.request;

import com.trustamarket.paymentservice.paymentservice.payment.domain.enums.PaymentTxType;

import java.util.UUID;

public record SearchPaymentTxRequest(
        UUID userId,
        PaymentTxType txType,
        Long minAmount,
        Long maxAmount,
        String pgResponseCode
) {
    public SearchPaymentTxRequest {
        if (minAmount == null) minAmount = 0L;
        if (maxAmount == null) maxAmount = Long.MAX_VALUE;
        if (minAmount > maxAmount) {
            throw new IllegalArgumentException("검색 최소 금액은 최대 금액보다 작아야합니다.");
        }
    }
}
