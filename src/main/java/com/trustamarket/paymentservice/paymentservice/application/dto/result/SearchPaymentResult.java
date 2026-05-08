package com.trustamarket.paymentservice.paymentservice.application.dto.result;

import com.trustamarket.paymentservice.paymentservice.domain.entity.Payment;
import com.trustamarket.paymentservice.paymentservice.domain.enums.PaymentStatus;

import java.time.Instant;
import java.util.UUID;

public record SearchPaymentResult(
        UUID paymentId,
        UUID userId,
        long amount,
        PaymentStatus status,
        Instant createdAt,
        Instant updatedAt
) {
    public static SearchPaymentResult from(Payment payment) {
        return new SearchPaymentResult(
                payment.getPaymentId(),
                payment.getUserId(),
                payment.getAmount(),
                payment.getPaymentStatus(),
                payment.getCreatedAt(),
                payment.getUpdatedAt()
        );
    }
}
