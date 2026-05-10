package com.trustamarket.paymentservice.paymentservice.payout.application.port.out.pgClient;

import com.trustamarket.paymentservice.paymentservice.payout.domain.enums.PayoutStatus;

public record PgPayoutResult(
        PayoutStatus status,
        String failReason
) {
    public PgPayoutResult(PayoutStatus status, String failReason) {
        this.status = status;
        this.failReason = failReason;
    }
}
