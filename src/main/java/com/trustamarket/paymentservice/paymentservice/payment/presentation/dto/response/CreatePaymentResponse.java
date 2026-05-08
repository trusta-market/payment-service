package com.trustamarket.paymentservice.paymentservice.payment.presentation.dto.response;

import com.trustamarket.paymentservice.paymentservice.payment.application.dto.result.CreatePaymentResult;

import java.time.Instant;
import java.util.UUID;

public record CreatePaymentResponse (
        UUID paymentId,
        long amount,
        Instant createdAt
){
    public static CreatePaymentResponse from(CreatePaymentResult result) {
        return new CreatePaymentResponse(
                result.paymentId(),
                result.amount(),
                result.createdAt()
        );
    }
}
