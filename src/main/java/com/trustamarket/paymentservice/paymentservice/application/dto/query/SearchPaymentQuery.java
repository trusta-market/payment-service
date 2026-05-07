package com.trustamarket.paymentservice.paymentservice.application.dto.query;

import com.trustamarket.paymentservice.paymentservice.domain.enums.PaymentStatus;

import java.time.Instant;
import java.util.UUID;

public record SearchPaymentQuery(
        UUID paymentId,
        UUID userId,
        long minAmount,
        long maxAmount,
        PaymentStatus status,
        Instant paidAt
) {}
