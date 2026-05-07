package com.trustamarket.paymentservice.paymentservice.presentation.dto.response;

import java.time.Instant;
import java.util.UUID;

public record SearchPaymentResponse (
        UUID paymentId,
        UUID userId,
        long amount,
        Instant createdAt
) {

}
