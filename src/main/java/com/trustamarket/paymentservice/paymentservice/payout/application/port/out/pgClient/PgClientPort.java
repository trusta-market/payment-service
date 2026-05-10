package com.trustamarket.paymentservice.paymentservice.payout.application.port.out.pgClient;

import com.trustamarket.paymentservice.paymentservice.payout.application.port.out.user.UserAccount;
import com.trustamarket.paymentservice.paymentservice.payout.domain.entity.Payout;

public interface PgClientPort {
    PgPayoutResult requestPayout(Payout payout, UserAccount account);
}
