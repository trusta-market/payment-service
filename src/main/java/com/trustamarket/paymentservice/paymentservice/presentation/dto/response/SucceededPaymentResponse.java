package com.trustamarket.paymentservice.paymentservice.presentation.dto.response;

import com.trustamarket.paymentservice.paymentservice.application.dto.result.SucceededPaymentResult;

import java.time.Instant;
import java.util.UUID;

public record SucceededPaymentResponse(
        UUID paymentId,
        String paymentStatus,
        long amount,
        Instant updatedAt
){
    public static SucceededPaymentResponse from(SucceededPaymentResult result){
        return new SucceededPaymentResponse(
                result.paymentId(),
                result.paymentStatus().name(),
                result.amount(),
                result.updatedAt()
        );
    }
}
