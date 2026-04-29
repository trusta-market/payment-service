package com.trustamarket.paymentservice.paymentservice.presentation.dto.response;

import com.trustamarket.paymentservice.paymentservice.application.dto.result.CreatePaymentResult;

import java.time.Instant;
import java.util.UUID;

public record CreatePaymentResponse (
        UUID paymentId,
        UUID chargeId,
        String paymentStatus,
        long amount,
        Instant createdAt
){
    public static CreatePaymentResponse from(CreatePaymentResult result) {
        return new CreatePaymentResponse(
                result.paymentId(),
                result.chargeId(),
                result.paymentStatus().name(),
                result.amount(),
                result.createdAt()
        );
    }
}
