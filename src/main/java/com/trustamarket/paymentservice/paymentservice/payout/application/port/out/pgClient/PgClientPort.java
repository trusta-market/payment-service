package com.trustamarket.paymentservice.paymentservice.payout.application.port.out.pgClient;

public interface PgClientPort {
    PgPayoutResult requestPayout(PgPayoutRequest request);
}
