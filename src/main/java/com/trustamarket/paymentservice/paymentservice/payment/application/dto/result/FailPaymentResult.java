package com.trustamarket.paymentservice.paymentservice.payment.application.dto.result;

import com.trustamarket.paymentservice.paymentservice.payment.domain.entity.Payment;
import com.trustamarket.paymentservice.paymentservice.payment.domain.enums.PaymentStatus;

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
