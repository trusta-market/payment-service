package com.trustamarket.paymentservice.paymentservice.presentation.dto.response;

import com.trustamarket.paymentservice.paymentservice.domain.enums.PaymentStatus;

import java.time.Instant;
import java.util.UUID;

public record FailPaymentResponse(
        UUID paymentId,
        PaymentStatus paymentStatus,
        Instant updatedAt
){}
