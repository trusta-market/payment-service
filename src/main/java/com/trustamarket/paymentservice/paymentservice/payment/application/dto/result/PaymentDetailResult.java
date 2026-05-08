package com.trustamarket.paymentservice.paymentservice.payment.application.dto.result;

import com.trustamarket.paymentservice.paymentservice.payment.domain.entity.Payment;
import com.trustamarket.paymentservice.paymentservice.payment.domain.enums.PaymentStatus;

import java.time.Instant;
import java.util.UUID;

public record PaymentDetailResult(
        UUID paymentId,
        UUID userId,
        long amount,
        PaymentStatus status,
        Instant createdAt,
        Instant updatedAt
) {
    public static PaymentDetailResult from(Payment payment) {
        return new PaymentDetailResult(
                payment.getPaymentId(),
                payment.getUserId(),
                payment.getAmount(),
                payment.getPaymentStatus(),
                payment.getCreatedAt(),
                payment.getUpdatedAt()
        );
    }
}
