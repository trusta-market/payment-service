package com.trustamarket.paymentservice.paymentservice.application.dto.result;

import com.trustamarket.paymentservice.paymentservice.domain.entity.Payment;
import com.trustamarket.paymentservice.paymentservice.domain.enums.PaymentStatus;

import java.time.Instant;
import java.util.UUID;

public record SucceededPaymentResult(
        UUID paymentId,
        PaymentStatus paymentStatus,
        long amount,
        Instant updatedAt
){
    public static SucceededPaymentResult from(Payment payment) {
        return new SucceededPaymentResult(
                payment.getPaymentId(),
                payment.getPaymentStatus(),
                payment.getAmount(),
                payment.getUpdatedAt()
        );
    }
}
