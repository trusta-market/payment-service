package com.trustamarket.paymentservice.paymentservice.application.dto.result;

import com.trustamarket.paymentservice.paymentservice.domain.enums.PaymentStatus;

import java.time.Instant;
import java.util.UUID;

public record SucceededPaymentResult(
        UUID paymentId,
        PaymentStatus paymentStatus,
        long amount,
        Instant updatedAt
){}
