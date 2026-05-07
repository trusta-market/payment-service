package com.trustamarket.paymentservice.paymentservice.application.dto.result;

import java.time.Instant;
import java.util.UUID;

public record SearchPaymentResult(
        UUID userId,
        UUID paymentId,
        long amount,
        Instant createdAt
) {
}
