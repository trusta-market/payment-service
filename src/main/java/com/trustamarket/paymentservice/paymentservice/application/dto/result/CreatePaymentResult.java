package com.trustamarket.paymentservice.paymentservice.application.dto.result;

import com.trustamarket.paymentservice.paymentservice.domain.entity.Payment;
import com.trustamarket.paymentservice.paymentservice.domain.enums.PaymentStatus;

import java.time.Instant;
import java.util.UUID;

public record CreatePaymentResult (
    UUID paymentId,
    UUID chargeId,
    PaymentStatus paymentStatus,
    long amount,
    Instant createdAt
){
    public static CreatePaymentResult from(Payment payment) {
        return new CreatePaymentResult(
                payment.getPaymentId(),
                payment.getChargeId(),
                payment.getPaymentStatus(),
                payment.getAmount(),
                payment.getCreatedAt()
        );
    }
}
