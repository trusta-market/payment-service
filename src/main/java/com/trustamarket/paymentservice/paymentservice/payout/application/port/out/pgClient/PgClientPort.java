package com.trustamarket.paymentservice.paymentservice.payout.application.port.out.pgClient;

import com.trustamarket.paymentservice.paymentservice.payout.infrastructure.pgMock.PgPayoutRequest;

public interface PgClientPort {
    PgPayoutResult requestPayout(PgPayoutRequest request);
}
