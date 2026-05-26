package com.trustamarket.paymentservice.paymentservice.payout.application.port.out.wallet;

public interface WalletPort {
    void payoutCompleted(PayoutCompletedResult result);
}
