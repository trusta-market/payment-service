package com.trustamarket.paymentservice.paymentservice.payment.application.dto.query;

import com.trustamarket.paymentservice.paymentservice.payment.domain.enums.PaymentTxType;

import java.util.UUID;

public record PaymentTxSearchQuery(
        UUID userId,
        PaymentTxType txType,
        long minAmount,
        long maxAmount,
        String pgResponseCode
) {}
