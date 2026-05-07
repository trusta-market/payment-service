package com.trustamarket.paymentservice.paymentservice.presentation.dto.response;

import com.trustamarket.paymentservice.paymentservice.application.dto.result.PaymentDetailResult;
import com.trustamarket.paymentservice.paymentservice.domain.enums.PaymentStatus;

import java.time.Instant;
import java.util.UUID;

public record PaymentDetailResponse (
        UUID paymentId,
        UUID userId,
        long amount,
        PaymentStatus status,
        String paymentKey,
        Instant createdAt,
        Instant updatedAt
) {
    public static PaymentDetailResponse from(PaymentDetailResult result) {
        return new PaymentDetailResponse(
                result.paymentId(),
                result.userId(),
                result.amount(),
                result.status(),
                result.paymentKey(),
                result.createdAt(),
                result.updatedAt()
        );
    }
}
