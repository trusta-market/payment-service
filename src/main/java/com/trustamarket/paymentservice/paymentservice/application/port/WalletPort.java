package com.trustamarket.paymentservice.paymentservice.application.port;

import java.util.UUID;

public interface WalletPort {
    void pointToWallet(UUID paymentId, long amount);
}
