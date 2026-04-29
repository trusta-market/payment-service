package com.trustamarket.paymentservice.paymentservice.presentation.dto.response;

import com.trustamarket.paymentservice.paymentservice.application.dto.result.FailPaymentResult;

import java.time.Instant;
import java.util.UUID;

public record FailPaymentResponse(
        UUID paymentId,
        String paymentStatus,
        Instant updatedAt
){
    public static FailPaymentResponse from(FailPaymentResult result) {
        return new FailPaymentResponse(
                result.paymentId(),
                result.paymentStatus().name(),
                result.updatedAt()
        );
    }
}
