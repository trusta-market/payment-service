package com.trustamarket.paymentservice.paymentservice.domain.repository.condition;

import com.trustamarket.paymentservice.paymentservice.domain.enums.PaymentStatus;

import java.util.UUID;

public record PaymentSearchCondition(
        UUID paymentId,
        UUID userId,
        long minAmount,
        long maxAmount,
        PaymentStatus status
) {}
