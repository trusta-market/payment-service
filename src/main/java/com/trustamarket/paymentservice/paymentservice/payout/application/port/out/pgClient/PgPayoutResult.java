package com.trustamarket.paymentservice.paymentservice.payout.application.port.out.pgClient;

public record PgPayoutResult(
        String status,
        String failReason
) {}
