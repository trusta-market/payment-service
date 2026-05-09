package com.trustamarket.paymentservice.paymentservice.payout.presentation.dto.response;

import com.trustamarket.paymentservice.paymentservice.payout.application.dto.result.CreatePayoutResult;

import java.time.Instant;
import java.util.UUID;

public record CreatePayoutResponse(
        UUID payoutId,
        UUID pointTxHistoryId,
        long amount,
        Instant createdAt
) {
    public static CreatePayoutResponse from(CreatePayoutResult result) {
        return new CreatePayoutResponse(
                result.userId(),
                result.pointTxHistoryId(),
                result.withdrawAmount(),
                result.createdAt()
        );
    }
}
