package com.trustamarket.paymentservice.paymentservice.presentation.dto.response;

import com.trustamarket.paymentservice.paymentservice.domain.enums.PaymentStatus;

import java.time.Instant;
import java.util.UUID;

public record PaymentDetailResponse (
        UUID paymentId,
        UUID userId,
        long amount,
        PaymentStatus status,
        String paymentKey,
        Instant createdAt
) {}
