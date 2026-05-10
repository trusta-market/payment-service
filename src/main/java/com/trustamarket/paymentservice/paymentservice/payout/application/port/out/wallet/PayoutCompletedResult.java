package com.trustamarket.paymentservice.paymentservice.payout.application.port.out.wallet;

import com.trustamarket.paymentservice.paymentservice.payout.domain.entity.Payout;
import com.trustamarket.paymentservice.paymentservice.payout.domain.enums.PayoutStatus;

import java.util.UUID;

public record PayoutCompletedResult (
        UUID userId,
        UUID payoutId,
        UUID pointTxRequestHistory,
        PayoutStatus status,
        long amount
) {
    public static PayoutCompletedResult from(Payout payout) {
        return new PayoutCompletedResult(
            payout.getUserId(),
            payout.getPayoutId(),
            payout.getPointTxRequestHistoryId(),
            payout.getStatus(),
            payout.getAmount()
        );
    }
}