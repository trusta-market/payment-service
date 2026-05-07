package com.trustamarket.paymentservice.paymentservice.presentation.dto.request;

import com.trustamarket.paymentservice.paymentservice.domain.enums.PaymentStatus;

import java.util.UUID;

public record SearchPaymentRequest (
        UUID paymentId,
        Long minAmount,
        Long maxAmount,
        PaymentStatus status
) {
    public SearchPaymentRequest {
        if (minAmount == null){ minAmount = 0L; }
        if (maxAmount == null){ maxAmount = Long.MAX_VALUE; }
    }
}
