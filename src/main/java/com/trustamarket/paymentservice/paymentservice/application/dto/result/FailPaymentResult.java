package com.trustamarket.paymentservice.paymentservice.application.dto.result;

import com.trustamarket.paymentservice.paymentservice.domain.entity.Payment;
import com.trustamarket.paymentservice.paymentservice.domain.enums.PaymentStatus;

import java.time.Instant;
import java.util.UUID;

public record FailPaymentResult (
        UUID paymentId,
        PaymentStatus paymentStatus,
        Instant updatedAt
) {
    public static FailPaymentResult from(Payment payment) {
        return new FailPaymentResult(
                payment.getPaymentId(),
                payment.getPaymentStatus(),
                payment.getUpdatedAt()
        );
    }
}
