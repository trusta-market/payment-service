package com.trustamarket.paymentservice.paymentservice.payout.application.dto.result;

import com.trustamarket.paymentservice.paymentservice.payout.domain.entity.Payout;

import java.time.Instant;
import java.util.UUID;

public record CreatePayoutResult(
        UUID userId,
        UUID pointTxHistoryId,
        long withdrawAmount,
        Instant createdAt
) {
    public static CreatePayoutResult from(Payout payout) {
        return new CreatePayoutResult(
                payout.getUserId(),
                payout.getPointTxHistoryId(),
                payout.getAmount(),
                payout.getCreatedAt()
        );
    }
}
