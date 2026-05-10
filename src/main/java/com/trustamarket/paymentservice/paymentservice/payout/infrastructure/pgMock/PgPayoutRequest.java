package com.trustamarket.paymentservice.paymentservice.payout.infrastructure.pgMock;

import com.trustamarket.paymentservice.paymentservice.payout.application.port.out.user.UserAccount;
import com.trustamarket.paymentservice.paymentservice.payout.domain.entity.Payout;

import java.util.UUID;

public record PgPayoutRequest (
        UUID payoutId,
        long amount,
        String backCode,
        String accountNumber,
        String accountHodler
) {
    public static PgPayoutRequest of(Payout payout, UserAccount user){
        return new PgPayoutRequest(
                payout.getPayoutId(),
                payout.getAmount(),
                user.bankCode(),
                user.accountNumber(),
                user.accountHolder()
        );
    }
}
