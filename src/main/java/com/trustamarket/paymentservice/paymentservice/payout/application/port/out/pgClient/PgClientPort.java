package com.trustamarket.paymentservice.paymentservice.payout.application.port.out.pgClient;

import com.trustamarket.paymentservice.paymentservice.payout.application.port.out.user.UserAccount;
import com.trustamarket.paymentservice.paymentservice.payout.infrastructure.pgMock.PgPayoutRequest;

public interface PgClientPort {
    PgPayoutResult requestPayout(PgPayoutRequest request, UserAccount account);
}
