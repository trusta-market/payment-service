package com.trustamarket.paymentservice.paymentservice.application.dto.result;

import com.trustamarket.paymentservice.paymentservice.domain.enums.PaymentStatus;

import java.time.Instant;
import java.util.UUID;

public record CreatePaymentResult (
    UUID paymentId,
    UUID chargeId,
    PaymentStatus paymentStatus,
    long amount,
    Instant createdAt
){}
