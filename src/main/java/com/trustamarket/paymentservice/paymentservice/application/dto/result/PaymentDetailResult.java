package com.trustamarket.paymentservice.paymentservice.application.dto.result;

import com.trustamarket.paymentservice.paymentservice.domain.enums.PaymentStatus;

import java.time.Instant;
import java.util.UUID;

public record PaymentDetailResult(
        UUID userId,
        UUID paymentId,
        long amount,
        PaymentStatus status,
        String paymentKey,
        Instant createdAt,
        Instant updatedAt
) {
}
