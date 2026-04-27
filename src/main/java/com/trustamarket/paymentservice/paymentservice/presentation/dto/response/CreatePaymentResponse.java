package com.trustamarket.paymentservice.paymentservice.presentation.dto.response;

import java.time.Instant;
import java.util.UUID;

public record CreatePaymentResponse (
        UUID paymentId,
        UUID chargeId,
        String paymentStatus,
        long amount,
        Instant createdAt
){}
