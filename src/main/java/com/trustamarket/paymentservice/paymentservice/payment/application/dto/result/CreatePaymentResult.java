package com.trustamarket.paymentservice.paymentservice.payment.application.dto.result;

import com.trustamarket.paymentservice.paymentservice.payment.domain.entity.Payment;
import com.trustamarket.paymentservice.paymentservice.payment.domain.enums.PaymentStatus;

import java.time.Instant;
import java.util.UUID;

public record CreatePaymentResult (
    UUID paymentId,
    long amount,
    Instant createdAt
){
    public static CreatePaymentResult from(Payment payment) {
        return new CreatePaymentResult(
                payment.getPaymentId(),
                payment.getAmount(),
                payment.getCreatedAt()
        );
    }
}
