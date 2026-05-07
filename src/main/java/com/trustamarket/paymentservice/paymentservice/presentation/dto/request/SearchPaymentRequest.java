package com.trustamarket.paymentservice.paymentservice.presentation.dto.request;

import com.trustamarket.paymentservice.paymentservice.domain.enums.PaymentStatus;

import java.time.Instant;
import java.util.UUID;

public record SearchPaymentRequest (
        UUID paymentId,
        long minAmount,
        long maxAmount,
        PaymentStatus status,
        Instant paidAt
) {}
