package com.trustamarket.paymentservice.paymentservice.payout.application.port.out.pgClient;

import com.trustamarket.paymentservice.paymentservice.payout.domain.enums.PayoutStatus;

import java.util.Objects;

public record PgPayoutResult(
        PayoutStatus status,
        String failReason
) {
    public PgPayoutResult(PayoutStatus status, String failReason) {
        this.status = Objects.requireNonNull(status, "status must not be null");
        this.failReason = failReason;
    }
}
