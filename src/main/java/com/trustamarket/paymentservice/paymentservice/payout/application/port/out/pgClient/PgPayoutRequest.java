package com.trustamarket.paymentservice.paymentservice.payout.application.port.out.pgClient;

import com.trustamarket.paymentservice.paymentservice.payout.application.port.out.user.UserAccount;
import com.trustamarket.paymentservice.paymentservice.payout.domain.entity.Payout;

import java.util.UUID;

public record PgPayoutRequest (
        UUID payoutId,
        long amount,
        String bankCode,
        String accountNumber,
        String accountHolder
) {
    public static PgPayoutRequest of(Payout payout, UserAccount user){
        if(payout.getAmount() <= 0) {
            throw new IllegalArgumentException("금액은 0보다 커야 합니다.");
        }
        return new PgPayoutRequest(
                payout.getPayoutId(),
                payout.getAmount(),
                user.bankCode(),
                user.accountNumber(),
                user.accountHolder()
        );
    }
}
