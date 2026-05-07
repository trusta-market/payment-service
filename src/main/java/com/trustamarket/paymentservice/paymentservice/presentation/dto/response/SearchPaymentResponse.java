package com.trustamarket.paymentservice.paymentservice.presentation.dto.response;

import com.trustamarket.paymentservice.paymentservice.application.dto.result.SearchPaymentResult;

import java.time.Instant;
import java.util.UUID;

public record SearchPaymentResponse (
        UUID paymentId,
        UUID userId,
        long amount,
        Instant createdAt,
        Instant updatedAt
) {
    public static SearchPaymentResponse from(SearchPaymentResult result){
        return new SearchPaymentResponse(
            result.paymentId(),
            result.userId(),
            result.amount(),
            result.createdAt(),
            result.updatedAt()
        );
    }
}
