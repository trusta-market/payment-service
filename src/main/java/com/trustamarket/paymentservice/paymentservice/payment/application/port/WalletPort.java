package com.trustamarket.paymentservice.paymentservice.payment.application.port;

import com.trustamarket.common.response.CommonResponse;
import java.util.UUID;

public interface WalletPort {
    CommonResponse<Void> pointToWallet(UUID userId, UUID paymentId, long amount);
}