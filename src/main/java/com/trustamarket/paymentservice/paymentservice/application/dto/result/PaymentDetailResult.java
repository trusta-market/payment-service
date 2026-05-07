package com.trustamarket.paymentservice.paymentservice.application.dto.result;

import com.trustamarket.paymentservice.paymentservice.domain.entity.Payment;
import com.trustamarket.paymentservice.paymentservice.domain.enums.PaymentStatus;

import java.time.Instant;
import java.util.UUID;

public record PaymentDetailResult(
        UUID paymentId,
        UUID userId,
        long amount,
        PaymentStatus status,
        String paymentKey,
        Instant createdAt,
        Instant updatedAt
) {
    public static PaymentDetailResult from(Payment payment) {
        return new PaymentDetailResult(
                payment.getPaymentId(),
                payment.getUserId(),
                payment.getAmount(),
                payment.getPaymentStatus(),
                payment.getPaymentKey(),
                payment.getCreatedAt(),
                payment.getUpdatedAt()
        );
    }
}
