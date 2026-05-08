package com.trustamarket.paymentservice.paymentservice.payment.application.dto.query;

import com.trustamarket.paymentservice.paymentservice.payment.domain.enums.PaymentStatus;

import java.util.UUID;

public record PaymentSearchQuery(
        UUID paymentId,
        UUID userId,
        long minAmount,
        long maxAmount,
        PaymentStatus status
) {}
